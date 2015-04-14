package com.tajpure.scheme;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jllvm.ExecutionEngine;
import org.jllvm.FunctionPassManager;
import org.jllvm.InstructionBuilder;
import org.jllvm.Module;
import org.jllvm.NativeLibrary;
import org.jllvm._type.DoubleType;
import org.jllvm._type.FunctionType;
import org.jllvm._type.Type;
import org.jllvm.bindings.LLVMLinkage;
import org.jllvm.bindings.LLVMRealPredicate;
import org.jllvm.generic.GenericValue;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.Value;
import org.jllvm.value.user.constant.Constant;
import org.jllvm.value.user.constant.ConstantReal;
import org.jllvm.value.user.constant.Function;
import org.jllvm.value.user.instruction.AddInstruction;
import org.jllvm.value.user.instruction.BranchInstruction;
import org.jllvm.value.user.instruction.CallInstruction;
import org.jllvm.value.user.instruction.FloatComparison;
import org.jllvm.value.user.instruction.IntegerToFloatCast;
import org.jllvm.value.user.instruction.MultiplyInstruction;
import org.jllvm.value.user.instruction.PhiNode;
import org.jllvm.value.user.instruction.ReturnInstruction;
import org.jllvm.value.user.instruction.SubtractInstruction;

abstract class ExprAST {
	public abstract Value codegen();
}

// / NumberExprAST - Expression class for numeric literals like "1.0".
class NumberExprAST extends ExprAST {
	double Val;

	public NumberExprAST(double val) {
		Val = val;
	}

	@Override
	public Value codegen() {
		return new ConstantReal(new DoubleType(), Val);
	}
};

// / VariableExprAST - Expression class for referencing a variable, like "a".
class VariableExprAST extends ExprAST {
	String Name;

	public VariableExprAST(String name) {
		Name = name;
	}

	@Override
	public Value codegen() {
		if (Kaleidoscope.NamedValues.containsKey(Name)) {
			return Kaleidoscope.NamedValues.get(Name);
		}
		return Kaleidoscope.ErrorV("Unknown variable name");
	}
}

// / BinaryExprAST - Expression class for a binary operator.
class BinaryExprAST extends ExprAST {
	char Op;
	ExprAST LHS, RHS;

	public BinaryExprAST(char op, ExprAST lhs, ExprAST rhs) {
		Op = op;
		LHS = lhs;
		RHS = rhs;
	}

	@Override
	public Value codegen() {
		Value L = LHS.codegen();
		Value R = RHS.codegen();
		if (L == null || R == null)
			return null;
		switch (Op) {
		case '+':
			return new AddInstruction(Kaleidoscope.builder, L, R, true,
					"addtmp");
		case '-':
			return new SubtractInstruction(Kaleidoscope.builder, L, R, true,
					"subtmp");
		case '*':
			return new MultiplyInstruction(Kaleidoscope.builder, L, R, true,
					"multmp");
		case '<':
			L = new FloatComparison(Kaleidoscope.builder,
					LLVMRealPredicate.LLVMRealULT, L, R, "cmptmp");
			// Convert bool 0/1 to double 0.0 or 1.0
			return new IntegerToFloatCast(Kaleidoscope.builder, L,
					new DoubleType(), "booltmp",
					IntegerToFloatCast.IntCastType.UNSIGNED);
		default:
			return Kaleidoscope.ErrorV("invalid binary operator");
		}
	}
}

// / CallExprAST - Expression class for function calls.
class CallExprAST extends ExprAST {
	String Callee;
	List<ExprAST> Args;

	public CallExprAST(String callee, List<ExprAST> args) {
		Callee = callee;
		Args = args;
	}

	@Override
	public Value codegen() {
		Function CalleeF = Kaleidoscope.TheModule.getNamedFunction(Callee);
		if (CalleeF == null)
			return Kaleidoscope.ErrorV("Unknown function referenced");
		if (CalleeF.countParameters() != Args.size())
			return Kaleidoscope.ErrorV("Incorrect # arguments passed");
		Value[] ArgsV = new Value[Args.size()];
		for (int i = 0; i < Args.size(); i++) {
			Value v = Args.get(i).codegen();
			if (v == null)
				return null;
			ArgsV[i] = v;
		}
		return new CallInstruction(Kaleidoscope.builder, CalleeF, ArgsV,
				"calltmp");
	}
};

// / PrototypeAST - This class represents the "prototype" for a function,
// / which captures its name, and its argument names (thus implicitly the number
// / of arguments the function takes).
class PrototypeAST extends ExprAST {
	String Name;
	List<String> Args;

	public PrototypeAST(String name, List<String> args) {
		Name = name;
		Args = args;
	}

	@Override
	public Value codegen() {
		DoubleType type = new DoubleType();
		Type[] Doubles = new Type[Args.size()];
		for (int i = 0; i < Args.size(); i++) {
			Doubles[i] = type;
		}
		FunctionType FT = new FunctionType(type, Doubles, false);
		Function F = new Function(Kaleidoscope.TheModule, Name, FT);
		F.setLinkage(LLVMLinkage.LLVMExternalLinkage);
		if (!F.getValueName().equals(Name)) {
			F.eraseFromParent();
			F = Kaleidoscope.TheModule.getNamedFunction(Name);
			if (F.countParameters() != Args.size()) {
				Kaleidoscope
						.ErrorF("redefinition of function with different # args");
				return null;
			}
		}
		for (int i = 0; i < F.countParameters(); i++) {
			F.getParameter(i).setValueName(Args.get(i));
			Kaleidoscope.NamedValues.put(Args.get(i), F.getParameter(i));
		}
		return F;
	}
}

// / FunctionAST - This class represents a function definition itself.
class FunctionAST extends ExprAST {
	PrototypeAST Proto;
	ExprAST Body;

	public FunctionAST(PrototypeAST proto, ExprAST body) {
		Proto = proto;
		Body = body;
	}

	@Override
	public Value codegen() {
		Kaleidoscope.NamedValues.clear();
		Function TheFunction = (Function) Proto.codegen();
		if (TheFunction == null)
			return null;
		BasicBlock BB = TheFunction.appendBasicBlock("entry");
		Kaleidoscope.builder.positionBuilderAtEnd(BB);
		Value RetVal = Body.codegen();
		if (RetVal != null) {
			new ReturnInstruction(Kaleidoscope.builder, RetVal);

			// Kaleidoscope.TheFPM.run(TheFunction);

			return TheFunction;
		}
		TheFunction.eraseFromParent();
		return null;
	}
};

class IfExprAST extends ExprAST {
	ExprAST Cond, Then, Else;

	public IfExprAST(ExprAST cond, ExprAST then, ExprAST _else) {
		Cond = cond;
		Then = then;
		Else = _else;
	}

	@Override
	public Value codegen() {
		Value CondV = Cond.codegen();
		if (CondV == null)
			return null;

		CondV = new FloatComparison(Kaleidoscope.builder,
				LLVMRealPredicate.LLVMRealONE, CondV, new ConstantReal(
						new DoubleType(), 0.0), "ifcond");
		Function TheFunction = Kaleidoscope.builder.getInsertBlock()
				.getParent();
		BasicBlock ThenBB = TheFunction.appendBasicBlock("then");
		BasicBlock ElseBB = TheFunction.appendBasicBlock("else");
		BasicBlock MergeBB = TheFunction.appendBasicBlock("ifcont");

		new BranchInstruction(Kaleidoscope.builder, CondV, ThenBB, ElseBB);
		Kaleidoscope.builder.positionBuilderAtEnd(ThenBB);
		Value ThenV = Then.codegen();
		if (ThenV == null)
			return null;
		new BranchInstruction(Kaleidoscope.builder, MergeBB);
		ThenBB = Kaleidoscope.builder.getInsertBlock();

		Kaleidoscope.builder.positionBuilderAtEnd(ElseBB);
		Value ElseV = Else.codegen();
		if (ElseV == null)
			return null;

		new BranchInstruction(Kaleidoscope.builder, MergeBB);
		ElseBB = Kaleidoscope.builder.getInsertBlock();

		Kaleidoscope.builder.positionBuilderAtEnd(MergeBB);

		PhiNode PN = new PhiNode(Kaleidoscope.builder, new DoubleType(),
				"iftmp");
		PN.addIncoming(new Value[] { ThenV, ElseV }, new BasicBlock[] { ThenBB,
				ElseBB });

		return PN;
	}
}

class ForExprAST extends ExprAST {
	String VarName;
	ExprAST Start, End, Step, Body;

	public ForExprAST(String varname, ExprAST start, ExprAST end, ExprAST step,
			ExprAST body) {
		VarName = varname;
		Start = start;
		End = end;
		Step = step;
		Body = body;
	}

	@Override
	public Value codegen() {
		// Emit the start code first, without 'variable' in scope.
		Value StartVal = Start.codegen();
		if (StartVal == null)
			return null;

		Function TheFunction = Kaleidoscope.builder.getInsertBlock()
				.getParent();
		BasicBlock PreheaderBB = Kaleidoscope.builder.getInsertBlock();
		BasicBlock LoopBB = TheFunction.appendBasicBlock("loop");

		new BranchInstruction(Kaleidoscope.builder, LoopBB);

		Kaleidoscope.builder.positionBuilderAtEnd(LoopBB);

		PhiNode Variable = new PhiNode(Kaleidoscope.builder, new DoubleType(),
				VarName);

		Value OldVal = null;
		if (Kaleidoscope.NamedValues.containsKey(VarName))
			OldVal = Kaleidoscope.NamedValues.get(VarName);
		Kaleidoscope.NamedValues.put(VarName, Variable);

		if (Body.codegen() == null)
			return null;

		Value StepVal = null;
		if (Step != null) {
			StepVal = Step.codegen();
			if (StepVal == null)
				return null;
		} else {
			StepVal = new ConstantReal(new DoubleType(), 1.0);
		}

		Value NextVar = new AddInstruction(Kaleidoscope.builder, Variable,
				StepVal, true, "nextvar");

		Value EndCond = End.codegen();
		if (EndCond == null)
			return null;

		EndCond = new FloatComparison(Kaleidoscope.builder,
				LLVMRealPredicate.LLVMRealONE, EndCond, new ConstantReal(
						new DoubleType(), 0.0), "loopcond");

		BasicBlock LoopEndBB = Kaleidoscope.builder.getInsertBlock();
		BasicBlock AfterBB = TheFunction.appendBasicBlock("afterloop");

		new BranchInstruction(Kaleidoscope.builder, EndCond, LoopBB, AfterBB);

		Kaleidoscope.builder.positionBuilderAtEnd(AfterBB);

		Variable.addIncoming(new Value[] { StartVal, NextVar },
				new BasicBlock[] { PreheaderBB, LoopEndBB });

		if (OldVal != null) {
			Kaleidoscope.NamedValues.put(VarName, OldVal);
		} else {
			Kaleidoscope.NamedValues.remove(VarName);
		}

		return Constant.constNull(new DoubleType());
	}
}

public class Kaleidoscope {

	static Module TheModule;
	static InstructionBuilder builder;
	static ExecutionEngine TheExecutionEngine;
	static Map<String, Value> NamedValues = new HashMap<String, Value>();
	static FunctionPassManager TheFPM;

	// The lexer returns tokens [0-255] if it is an unknown character, otherwise
	// one
	// of these for known things.
	enum Token {
		tok_eof(65535), tok_def(-2), tok_extern(-3), tok_identifier(-4), tok_number(
				-5), tok_if(-6), tok_then(-7), tok_else(-8), tok_for(-9), tok_in(
				-10);

		private int value = 0;

		private Token(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		};
	};

	static String IdentifierStr; // Filled in if tok_identifier
	static double NumVal;

	static char LastChar = ' ';

	// / BinopPrecedence - This holds the precedence for each binary operator
	// that is
	// / defined.
	static Map<Character, Integer> BinopPrecedence = new HashMap<Character, Integer>();

	static boolean isalnum(char ch) {
		if (Character.isAlphabetic(ch) || Character.isDigit(ch))
			return true;
		return false;
	}

	// / GetTokPrecedence - Get the precedence of the pending binary operator
	// token.
	static int GetTokPrecedence() {
		if (!(CurTok >= 0 && CurTok <= 127))
			return -1;

		// Make sure it's a declared binop.
		Character c = (char) CurTok;
		if (!BinopPrecedence.containsKey(c))
			return -1;
		return BinopPrecedence.get(c);
	}

	// / Error* - These are little helper functions for error handling.
	static ExprAST Error(String Str) {
		System.err.println(Str);
		return null;
	}

	static PrototypeAST ErrorP(String Str) {
		Error(Str);
		return null;
	}

	static FunctionAST ErrorF(String Str) {
		Error(Str);
		return null;
	}

	static Value ErrorV(String Str) {
		Error(Str);
		return null;
	}

	// / identifierexpr
	// / ::= identifier
	// / ::= identifier '(' expression* ')'
	static ExprAST ParseIdentifierExpr() {
		String IdName = IdentifierStr;

		getNextToken(); // eat identifier.

		if (CurTok != '(') // Simple variable ref.
			return new VariableExprAST(IdName);

		// Call.
		getNextToken(); // eat (
		List<ExprAST> Args = new ArrayList<ExprAST>();
		if (CurTok != ')') {
			while (true) {
				ExprAST Arg = ParseExpression();
				if (Arg == null)
					return null;
				Args.add(Arg);

				if (CurTok == ')')
					break;

				if (CurTok != ',')
					return Error("Expected ')' or ',' in argument list");
				getNextToken();
			}
		}

		// Eat the ')'.
		getNextToken();

		return new CallExprAST(IdName, Args);
	}

	// / numberexpr ::= number
	static ExprAST ParseNumberExpr() {
		ExprAST Result = new NumberExprAST(NumVal);
		getNextToken(); // consume the number
		return Result;
	}

	// / parenexpr ::= '(' expression ')'
	static ExprAST ParseParenExpr() {
		getNextToken(); // eat (.
		ExprAST V = ParseExpression();
		if (V == null)
			return null;

		if (CurTok != ')')
			return Error("expected ')'");
		getNextToken(); // eat ).
		return V;
	}

	// / primary
	// / ::= identifierexpr
	// / ::= numberexpr
	// / ::= parenexpr
	static ExprAST ParsePrimary() {
		if (CurTok == Token.tok_identifier.value()) {
			return ParseIdentifierExpr();
		} else if (CurTok == Token.tok_number.value()) {
			return ParseNumberExpr();
		} else if (CurTok == '(') {
			return ParseParenExpr();
		} else if (CurTok == Token.tok_if.value()) {
			return ParseIfExpr();
		} else if (CurTok == Token.tok_for.value()) {
			return ParseForExpr();
		} else {
			return Error("unknown token when expecting an expression");
		}
	}

	// / binoprhs
	// / ::= ('+' primary)*
	static ExprAST ParseBinOpRHS(int ExprPrec, ExprAST LHS) {
		// If this is a binop, find its precedence.
		while (true) {
			int TokPrec = GetTokPrecedence();

			// If this is a binop that binds at least as tightly as the current
			// binop,
			// consume it, otherwise we are done.
			if (TokPrec < ExprPrec)
				return LHS;

			// Okay, we know this is a binop.
			int BinOp = CurTok;
			getNextToken(); // eat binop

			// Parse the primary expression after the binary operator.
			ExprAST RHS = ParsePrimary();
			if (RHS == null)
				return null;

			// If BinOp binds less tightly with RHS than the operator after RHS,
			// let
			// the pending operator take RHS as its LHS.
			int NextPrec = GetTokPrecedence();
			if (TokPrec < NextPrec) {
				RHS = ParseBinOpRHS(TokPrec + 1, RHS);
				if (RHS == null)
					return null;
			}

			// Merge LHS/RHS.
			LHS = new BinaryExprAST((char) BinOp, LHS, RHS);
		}
	}

	// / expression
	// / ::= primary binoprhs
	// /
	static ExprAST ParseExpression() {
		ExprAST LHS = ParsePrimary();
		if (LHS == null)
			return null;

		return ParseBinOpRHS(0, LHS);
	}

	// / prototype
	// / ::= id '(' id* ')'
	static PrototypeAST ParsePrototype() {
		if (CurTok != Token.tok_identifier.value())
			return ErrorP("Expected function name in prototype");

		String FnName = IdentifierStr;
		getNextToken();

		if (CurTok != '(')
			return ErrorP("Expected '(' in prototype");

		List<String> ArgNames = new ArrayList<String>();
		while (getNextToken() == Token.tok_identifier.value())
			ArgNames.add(IdentifierStr);
		if (CurTok != ')')
			return ErrorP("Expected ')' in prototype");

		// success.
		getNextToken(); // eat ')'.

		return new PrototypeAST(FnName, ArgNames);
	}

	// / definition ::= 'def' prototype expression
	static FunctionAST ParseDefinition() {
		getNextToken(); // eat def.
		PrototypeAST Proto = ParsePrototype();
		if (Proto == null)
			return null;

		ExprAST E = ParseExpression();
		if (E != null)
			return new FunctionAST(Proto, E);
		return null;
	}

	// / toplevelexpr ::= expression
	static FunctionAST ParseTopLevelExpr() {
		ExprAST E = ParseExpression();
		if (E != null) {
			// Make an anonymous proto.
			PrototypeAST Proto = new PrototypeAST("", new ArrayList<String>());
			return new FunctionAST(Proto, E);
		}
		return null;
	}

	// / external ::= 'extern' prototype
	static PrototypeAST ParseExtern() {
		getNextToken(); // eat extern.
		return ParsePrototype();
	}

	// / ifexpr ::= 'if' expression 'then' expression 'else' expression
	static ExprAST ParseIfExpr() {
		getNextToken(); // eat the if.

		ExprAST Cond = ParseExpression();
		if (Cond == null)
			return null;

		if (CurTok != Token.tok_then.value()) {
			return Error("expected then");
		}
		getNextToken(); // eat the then

		ExprAST Then = ParseExpression();
		if (Then == null)
			return null;

		if (CurTok != Token.tok_else.value()) {
			return Error("expected else");
		}

		getNextToken();

		ExprAST Else = ParseExpression();
		if (Else == null)
			return null;

		return new IfExprAST(Cond, Then, Else);
	}

	// / forexpr ::= 'for' identifier '=' expr ',' expr (',' expr)? 'in'
	// expression
	static ExprAST ParseForExpr() {
		getNextToken(); // eat the for.

		if (CurTok != Token.tok_identifier.value())
			return Error("expected identifier after for");

		String IdName = IdentifierStr;
		getNextToken(); // eat identifier.

		if (CurTok != '=')
			return Error("expected '=' after for");
		getNextToken(); // eat '='.

		ExprAST Start = ParseExpression();
		if (Start == null)
			return null;
		if (CurTok != ',')
			return Error("expected ',' after for start value");
		getNextToken();

		ExprAST End = ParseExpression();
		if (End == null)
			return null;

		// The step value is optional.
		ExprAST Step = null;
		if (CurTok == ',') {
			getNextToken();
			Step = ParseExpression();
			if (Step == null)
				return null;
		}

		if (CurTok != Token.tok_in.value())
			return Error("expected 'in' after for");
		getNextToken(); // eat 'in'.

		ExprAST Body = ParseExpression();
		if (Body == null)
			return null;

		return new ForExprAST(IdName, Start, End, Step, Body);
	}

	// ===----------------------------------------------------------------------===//
	// Top-Level parsing
	// ===----------------------------------------------------------------------===//

	static void HandleDefinition() {
		FunctionAST F = ParseDefinition();
		if (F != null) {
			Function LF = (Function) F.codegen();
			if (LF != null) {
				System.err.println("Read function definition:");
				LF.dump();
			}
		} else {
			// Skip token for error recovery.
			getNextToken();
		}
	}

	static void HandleExtern() {
		PrototypeAST P = ParseExtern();
		if (P != null) {
			Function F = (Function) P.codegen();
			if (F != null) {
				System.err.println("Read estern:");
				F.dump();
			}
		} else {
			// Skip token for error recovery.
			getNextToken();
		}
	}

	static void HandleTopLevelExpression() {
		// Evaluate a top-level expression into an anonymous function.
		FunctionAST F = ParseTopLevelExpr();
		if (F != null) {
			Function LF = (Function) F.codegen();
			if (LF != null) {
				System.err.println("Read top-level expression:");
				LF.dump();

				GenericValue[] args = new GenericValue[0];
				GenericValue ret = Kaleidoscope.TheExecutionEngine.runFunction(
						LF, args);
				System.err.println("Evaluated to "
						+ org.jllvm.bindings.ExecutionEngine
								.LLVMGenericValueToFloat(
										new DoubleType().getInstance(),
										ret.getInstance()));

			}
		} else {
			// Skip token for error recovery.
			getNextToken();
		}
	}

	// / top ::= definition | external | expression | ';'
	static void MainLoop() {
		while (true) {
			System.err.print("ready> ");
			if (CurTok == Token.tok_eof.value()) {
				return;
			} else if (CurTok == ';') {
				getNextToken();
			} else if (CurTok == Token.tok_def.value()) {
				HandleDefinition();
			} else if (CurTok == Token.tok_extern.value()) {
				HandleExtern();
			} else {
				HandleTopLevelExpression();
			}
		}
	}

	// / gettok - Return the next token from standard input.
	@SuppressWarnings("deprecation")
	static int gettok() throws IOException {

		// Skip any whitespace.
		while (Character.isSpace(LastChar))
			LastChar = (char) System.in.read();

		if (Character.isAlphabetic(LastChar)) { // identifier:
												// [a-zA-Z][a-zA-Z0-9]*
			IdentifierStr = "" + LastChar;
			while (isalnum((LastChar = (char) System.in.read())))
				IdentifierStr += LastChar;

			if (IdentifierStr.equals("def"))
				return Token.tok_def.value();
			if (IdentifierStr.equals("extern"))
				return Token.tok_extern.value();
			if (IdentifierStr.equals("if"))
				return Token.tok_if.value();
			if (IdentifierStr.equals("then"))
				return Token.tok_then.value();
			if (IdentifierStr.equals("else"))
				return Token.tok_else.value();
			if (IdentifierStr.equals("for"))
				return Token.tok_for.value();
			if (IdentifierStr.equals("in"))
				return Token.tok_in.value();
			return Token.tok_identifier.value();
		}

		if (Character.isDigit(LastChar) || LastChar == '.') { // Number: [0-9.]+
			String NumStr = new String();
			do {
				NumStr += LastChar;
				LastChar = (char) System.in.read();
			} while (Character.isDigit(LastChar) || LastChar == '.');

			NumVal = Double.parseDouble(NumStr);
			return Token.tok_number.value();
		}

		if (LastChar == '#') {
			// Comment until end of line.
			do
				LastChar = (char) System.in.read();
			while (LastChar != -1 && LastChar != '\n' && LastChar != '\r');

			if (LastChar != -1)
				return gettok();
		}

		// Check for end of file. Don't eat the EOF.
		if (LastChar == -1)
			return Token.tok_eof.value();

		// Otherwise, just return the character as its ascii value.
		int ThisChar = LastChar;
		LastChar = (char) System.in.read();
		return ThisChar;
	}

	// / CurTok/getNextToken - Provide a simple token buffer. CurTok is the
	// current
	// / token the parser is looking at. getNextToken reads another token from
	// the
	// / lexer and updates CurTok with its results.
	static int CurTok;

	static int getNextToken() {
		try {
			return CurTok = gettok();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void main(String[] args) {
		NativeLibrary.load();

		TheModule = new Module("my cool jit");
		builder = new InstructionBuilder();
		try {
			ExecutionEngine.linkInInterpreter();
			TheExecutionEngine = new ExecutionEngine(TheModule);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BinopPrecedence.put('<', 10);
		BinopPrecedence.put('+', 20);
		BinopPrecedence.put('-', 20);
		BinopPrecedence.put('*', 40);

		System.err.print("ready> ");
		getNextToken();

		FunctionPassManager OurFPM = new FunctionPassManager(TheModule);
		OurFPM.addTargetData(TheExecutionEngine.getTargetData());
		OurFPM.addBasicAliasAnalysisPass();
		OurFPM.addInstructionCombiningPass();
		OurFPM.addReassociatePass();
		OurFPM.addGVNPass();
		OurFPM.addCFGSimplificationPass();
		OurFPM.initialize();

		TheFPM = OurFPM;

		// Run the main "interpreter loop" now.
		MainLoop();

		TheModule.dump();
	}

}

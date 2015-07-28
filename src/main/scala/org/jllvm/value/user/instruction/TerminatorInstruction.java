package org.jllvm.value.user.instruction;


/* This class exists solely to mirror the C++ bindings and have a superclass for terminator instructions. */
public class TerminatorInstruction extends Instruction {
	public TerminatorInstruction() {
		instance = null;
	}
}

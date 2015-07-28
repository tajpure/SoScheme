package org.jllvm.value.user.instruction;

import org.jllvm._type.Type;


public abstract class CastInstruction extends Instruction {
	protected Type destination;
	
	public Type getDestination() {
		return destination;
	}
	
	public CastInstruction(Type destType) {
		destination = destType;
	}
}

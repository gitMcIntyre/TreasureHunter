package com.treasurehunter.model.impl;

import com.treasurehunter.model.Instruction;

/**
 * Concrete Instruction implementation to handle working with instruction
 * data.
 *  
 * @author dmcintyre
 *
 */
public class InstructionImpl implements Instruction {

	private String modeName;
	private String direction;
	private String travelTime;
	
	public InstructionImpl(String modeName, String direction, String travelTime) {
		this.modeName = modeName;
		this.direction = direction;
		this.travelTime = travelTime;
	}
	
	/* (non-Javadoc)
	 * @see com.treasurehunter.model.Instruction#getModeName()
	 */
	@Override
	public String getModeName() {
		return this.modeName;
	}

	/* (non-Javadoc)
	 * @see com.treasurehunter.model.Instruction#getTravelTime()
	 */
	@Override
	public String getTravelTime() {
		return this.travelTime;
	}

	/* (non-Javadoc)
	 * @see com.treasurehunter.model.Instruction#getDirection()
	 */
	@Override
	public String getDirection() {
		return this.direction;
	}

}

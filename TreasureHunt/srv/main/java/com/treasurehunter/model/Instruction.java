package com.treasurehunter.model;

/**
 * An Instruction has information about where the next point in the quest for
 * the treasure is located.
 * 
 * @author dmcintyre
 *
 */
public interface Instruction {
	
	/**
	 * The mode name is the string representation of the mode of transportation.
	 * For example "walk" or "run".
	 * 
	 * @return
	 * 		the string representation for the mode of transportation.
	 */
	public String getModeName();
	
	/**
	 * @return
	 * 		the travel time in minutes for the instruction.
	 */
	public String getTravelTime();
	
	/**
	 * 
	 * @return
	 * 		the direction in which to travel. For example "N" or "NE".
	 */
	public String getDirection();
}

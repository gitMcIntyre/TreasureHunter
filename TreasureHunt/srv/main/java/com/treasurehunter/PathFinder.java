package com.treasurehunter;

import java.util.List;
import java.util.Optional;

/**
 * Defines the methods for finding a treasure given a set of directions to follow.
 * 
 * @author dmcintyre
 *
 */
public interface PathFinder {

	/**
	 * Set the list of path directions to process
	 * @param paths
	 * 		the List of path strings
	 */
	public void setPaths(List<String> paths);

	/**
	 * Follow the directions and return the location of the treasure
	 * @return
	 * 		the Optional string that indicates the location of the treasure
	 */
	public Optional<String> findTreasure();
	
}

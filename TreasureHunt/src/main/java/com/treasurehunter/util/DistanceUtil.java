package com.treasurehunter.util;

import com.treasurehunter.ModeType;

/**
 * Utility class for returning distance information.
 * 
 * @author dmcintyre
 *
 */
public class DistanceUtil {

	private DistanceUtil() {
	}
	
	/**
	 * Find the distance traveled based on the ModeType and time.
	 * @param mode
	 * 		the ModeType associated to the mode of transportation
	 * @param minutes
	 * 		the time to travel
	 * @return
	 * 		the distance traveled for the correcsponding ModeType
	 */
	public static Double getDistance(ModeType mode, int minutes) {
		int speed = mode.getSpeed();
		
		return speed * (minutes / new Double(60));
	}

}
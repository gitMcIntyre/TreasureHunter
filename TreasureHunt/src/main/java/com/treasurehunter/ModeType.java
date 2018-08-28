package com.treasurehunter;

import java.util.Objects;

/**
 * Enumeration to encapsupate the modes of transportation as well
 * as mapping the mode to its speed.
 * 
 * @author dmcintyre
 *
 */
public enum ModeType {
	WALK("walk", 3),
	RUN("run", 6),
	HORSE_GALLOP("horse gallop", 15),
	HORSE_TROT("horse trot", 4),
	ELEPHANT_RIDE("elephant ride", 6),
	UNKNOWN(null, 0);
	
	
	private String modeType;
	private int speed;
	
	private ModeType(String modeType, int speed) {
		this.modeType = modeType;
		this.speed = speed;
	}
	
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * Return a ModeType based on a modeType string
	 * @param mode
	 * 		the modeType string for which to return the ModeType
	 * @return
	 * 		the corresponding ModeType if found; otherwise ModeType.UNKNOWN
	 */
	public static ModeType findMode(String mode) {
		
		if(Objects.isNull(mode)) {
			return ModeType.UNKNOWN;
		}
		
		ModeType foundMode = null;
		for(ModeType m : ModeType.values()) {
			if(m.modeType.equalsIgnoreCase(mode)) {
				foundMode = m;
				break;
			}
		}
		return foundMode;
	}
	
}

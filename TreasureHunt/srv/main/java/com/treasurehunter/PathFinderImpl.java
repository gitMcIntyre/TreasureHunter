package com.treasurehunter;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.treasurehunter.model.Instruction;
import com.treasurehunter.model.impl.InstructionImpl;
import com.treasurehunter.util.DistanceUtil;

/**
 * Process the direction instructions finding the last point in the path.
 * 
 * Instructions are expected to be of the form:
 * <ModeType>,<#> hour <#> mins,<direction>
 * Minutes or hours or a combination of the two are required.
 * 
 * For Example:
 * Walk,3 hours 30 mins,N
 * Run,60 mins,SW
 * 
 * USE: given a point x,y, the distance n traveled and the direction traveled we
 * can determine theta to find the point x1,y1: 
 * x1 = x + n cos(theta) 
 * y1 = y + n sin(theta)
 * 
 * @see com.treasurehunter.ModeType for the list of supported modes of transportation
 * @author dmcintyre
 *
 */
public class PathFinderImpl implements PathFinder {

	private static final String SOUTH = "South";

	private static final String WEST = "West";

	private static final String EAST = "East";

	private static final String NORTH = "North";

	//The list of path information
	List<String> paths;
	
	//The current point along the path. Default to 0,0
	Point2D.Double lastPoint = new Point2D.Double(0, 0);

	/* (non-Javadoc)
	 * @see com.treasurehunter.PathFinder#setPaths(java.util.List)
	 */
	@Override
	public void setPaths(List<String> paths) {
		this.paths = paths;

	}

	/* (non-Javadoc)
	 * @see com.treasurehunter.PathFinder#findTreasure()
	 */
	@Override
	public Optional<String> findTreasure() {

		if (Objects.isNull(paths)) {
			return Optional.ofNullable(null);
		}
		
		// Process the direction instructions
		paths.stream()
			.filter(Objects::nonNull)
			.map(this::createInstruction)
			.forEach(this::followInstruction);

		return Optional.of(buildOutput());
	}

	/**
	 * Build the output based on the last point in the path.
	 * @return
	 * 		A string instructing the user along the straightest path to the treasure.
	 * 		For example: 
	 * 			1) 3 mile(s) to the North
	 * 			2) 3 mile(s) to the North, 1 mile(s) to the West
	 */
	private String buildOutput() {
		Double x = lastPoint.getX();
		Double y = lastPoint.getY();

		//truncate the values to 2 decimals
		Double tx = BigDecimal.valueOf(x).setScale(2, RoundingMode.HALF_UP).doubleValue();
		Double ty = BigDecimal.valueOf(y).setScale(2, RoundingMode.HALF_UP).doubleValue();

		String north_south = null;
		String east_west = null;

		//Determine the quadrant (compass direction)
		if (tx > 0 && ty > 0) {
			north_south = NORTH;
			east_west = EAST;
		} else if (tx < 0 && ty > 0) {
			north_south = NORTH;
			east_west = WEST;
		} else if (tx < 0 && ty < 0) {
			north_south = SOUTH;
			east_west = WEST;
		} else if (tx > 0 && ty < 0) {
			north_south = SOUTH;
			east_west = EAST;
		} else if (tx == 0 && ty > 0) {
			north_south = NORTH;
		} else if (tx == 0 && ty < 0)
			north_south = SOUTH;
		else if (ty == 0 && tx < 0)
			east_west = WEST;
		else if (ty == 0 && tx > 0)
			east_west = EAST;
		else {
			// x=0, y=0...the hunter is standing on the treasure
			return "You have arrived.";
		}

		String output = "%s mile(s) to the %s";
		StringBuilder sb = new StringBuilder();
		;
		if (north_south != null) {
			sb.append(String.format(output, new DecimalFormat("#.##").format(Math.abs(ty)), north_south));
		}
		if (east_west != null) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(String.format(output, new DecimalFormat("#.##").format(Math.abs(tx)), east_west));
		}
		return sb.toString();
	}

	/**
	 * Split the instruction string into its parts
	 * @param instruction
	 * 		the instruction string of the form: <ModeType>, <travel time>, <direction>
	 * @return
	 * 		the list of instruction data
	 */
	protected Instruction createInstruction(String instruction) {
		if (instruction == null) {
			return null;
		}

		List<String> inst = Arrays.asList(instruction.split(","));
		if(Objects.nonNull(inst) && inst.size() >= 3) {
			return new InstructionImpl(inst.get(0), inst.get(2), inst.get(1));
		}
		return null;
	}

	/**
	 * Process the instruction information and calculate the point along the path.
	 * 
	 * @param instruction
	 * 		the path instruction information
	 */
	protected void followInstruction(Instruction instruction) {
		ModeType modeType = ModeType.findMode(instruction.getModeName());
		String time = instruction.getTravelTime();
		String direction = instruction.getDirection();

		// Simple validation on data.
		if (modeType == ModeType.UNKNOWN || time == null || direction == null) {
			return;
		}
		
		// Determine the angle based on the direction in the instruction
		Double theta = 0.0;
		switch (direction) {
		case "N":
			theta = 90.0;
			break;
		case "S":
			theta = 270.0;
			break;
		case "E":
			theta = 0.0;
			break;
		case "W":
			theta = 180.0;
			break;
		case "NW":
			theta = 135.0;
			break;
		case "NE":
			theta = 45.0;
			break;
		case "SW":
			theta = 225.0;
			break;
		case "SE":
			theta = 315.0;
			break;
		}
		
		// Given the distance traveled from the last point, calculate the point along the path
		Double dis = DistanceUtil.getDistance(modeType, getMinutes(time));
		Double x1 = lastPoint.getX() + dis * Math.cos(Math.toRadians(theta));
		Double y1 = lastPoint.getY() + dis * Math.sin(Math.toRadians(theta));

		// Update the last point visited
		lastPoint.setLocation(x1, y1);

	}

	/**
	 * Find the minutes to travel by parsing the travel time information in the path instruction
	 * 
	 * @param timeString
	 * 		the travel time information from the instruction
	 * @return
	 * 		the travel time in minutes
	 */
	protected Integer getMinutes(String timeString) {
		// Find the groups of the word hour preceeded by a number 
		// or the word 'min' precceded by a number
		String patternString = "((\\d+)(\\shour))?((\\d+)(\\smin))?";
		String h = null;
		String m = null;
		Integer minutes = 0;
		
		// Find matches
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(timeString);
		while (matcher.find()) {
			// Depending on the match the group could be null
			// so we need to check for null values here.
			
			// group 2 is the hours
			if (matcher.group(2) != null) {
				h = matcher.group(2);
			}

			// group 5 is the minutes
			if (matcher.group(5) != null) {
				m = matcher.group(5);
			}
		}

		// Since hours and minutes are optional then we need
		// add the time only if it is specified in the timeString
		if (h != null) {
			Integer hours = Integer.parseInt(h);
			minutes += hours * 60;

		}

		if (m != null) {
			minutes += Integer.parseInt(m);
		}
		
		return minutes;
	}
}

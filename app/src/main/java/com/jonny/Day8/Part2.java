package com.jonny.Day8;

import java.util.*;

import com.jonny.Helpers;

public class Part2 {

	private static Map<Character, List<List<Integer>>> antennae;
	private static Set<List<Integer>> antinodes;

	private static int xLimit;
	private static int yLimit;

	private static void parseInput(List<String> lines) {

		setLimits(lines);

		Part2.antennae = new HashMap<>();

		//read it in starting at the bottom, so that positive y goes up later on
		for (int y = lines.size() - 1; y >= 0; y--) {

			String line = lines.get(y);

			for (int x = 0; x < line.length(); x++) {

				if (line.charAt(x) != '.') {

					//because we're reading it in from bottom to top, find actual y coordinate of antenna
					int yCoord = Part2.yLimit - y;
					
					Part2.antennae.putIfAbsent(line.charAt(x), new ArrayList<>());

					antennae.get(line.charAt(x)).add(List.of(x, yCoord));
				}
			}
		}
	}

	private static void setLimits(List<String> lines) {

		Part2.yLimit = lines.size() - 1;
		Part2.xLimit = lines.get(0).length() - 1;
	}

	private static void findAntinodes() {

		Part2.antinodes = new HashSet<>();

		for (Map.Entry<Character, List<List<Integer>>> character: Part2.antennae.entrySet()) {
			
			Character targetChar = character.getKey();
			List<List<Integer>> locations = character.getValue();

			//if only 1 occurence of character, there can be no antinodes
			if (locations.size() == 1) {
				continue;
			}

			//for each location of a character, check for antinodes (could find all combinations using 
			//recursion but doubt it would save much time here)
			for (List<Integer> location: locations) {
				
				checkLocation(location, locations);
			}
		}
	}

	private static void checkLocation(List<Integer> originLocation, List<List<Integer>> characterLocations) {

		for (List<Integer> targetLocation : characterLocations) {

			//if origin location = targetLocation, dont bother checking
			if (targetLocation.equals(originLocation)) {
				continue;
			}

			createAntinodes(originLocation, targetLocation);	
		}
	}

	private static void createAntinodes(List<Integer> originLocation, List<Integer> targetLocation) {

		//find difference between two locations
		int xDiff = targetLocation.get(0) - originLocation.get(0);
		int yDiff = targetLocation.get(1) - originLocation.get(1);

		//add an antinode for both target and original location
		Part2.antinodes.add(originLocation);
		Part2.antinodes.add(targetLocation);

		//initialise antinodeX and Y
		int antinodeX = targetLocation.get(0);
		int antinodeY = targetLocation.get(1);
		
		while(true) {

			antinodeX += xDiff;	
			antinodeY += yDiff;

			//if either has exceeded bounds of grid, break
			if (antinodeX > Part2.xLimit || antinodeX < 0 || antinodeY > Part2.yLimit || antinodeY < 0) {
				break;
			}

			//otherwise add antinode and keep going
			Part2.antinodes.add(List.of(antinodeX, antinodeY));
		}
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("8");

		parseInput(lines);

		findAntinodes();

		System.out.println(Part2.antinodes.size());
	}
}

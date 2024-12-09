package com.jonny.Day6;

import java.util.*;

import com.jonny.Helpers;

public class Part2 {

	private static List<Character[]> grid;

	private static List<Character[]> parseInput(List<String> lines) {

		Part2.grid = new ArrayList<>();

		//read it in starting at the bottom, so that positive y goes up later on
		for (int i = lines.size() - 1; i >= 0; i--) {

			String line = lines.get(i);

			Character[] lineArray = new Character[line.length()];

			for (int j = 0; j < line.length(); j++) {

				lineArray[j] = line.charAt(j);
			}

			Part2.grid.add(lineArray);
		}


		return Part2.grid;
	}

	private static class Guard {

		private List<Character[]> grid;
		private int x;
		private int y;
		private int facing; //direction is measured in degrees
		private Set<String> visited; //set of already visited locations, given a key later on

		private int bounds; //max X or Y of grid

		//init to set grid with extra obstacle, bounds, and init visited
		private Guard(List<Character[]> grid) {
			this.grid = grid;

			bounds = grid.get(0).length - 1;

			visited = new HashSet<>();
		}

		private void findGuardLoc() {

			for (int y = 0; y < grid.get(0).length; y++) {

				Character[] row = grid.get(y);

				for (int x = 0; x < row.length; x++) {

					if (row[x].equals('^')) {
						// debug line
						// System.out.println("Found guard at " + x + " " + y);

						setGuardLoc(x, y);
						setGuardFacing(0);
					}
				}
			}
		}

		private void setGuardLoc(int x, int y) {
			this.x = x;
			this.y = y;
		}

		private void setGuardFacing(int facing) {
			this.facing = facing;
		}

		private boolean checkForObstacle() {
			//returns true if it find an obstacle in front

			Character inFront = 'Q';
			
			try {
				switch (facing) {
					case 0:
						inFront = grid.get(y + 1)[x];
						break;
					case 90:
						inFront = grid.get(y)[x + 1];
						break;
					case 180:
						inFront = grid.get(y - 1)[x];
						break;
					case 270:
						inFront = grid.get(y)[x - 1];
						break;
				}
			} catch (Exception e) {
				//return false if in front of guard is outside bounds of area
				return false;
			}

			

			if (inFront == '#') {
				return true;
			}

			return false;
		}

		private String moveGuard() {

			//give the location a key
			String locationKey = x + "," + y + "," + facing;

			//check if locationKey is in visited, if it is, it must have looped
			if(visited.contains(locationKey)) {
				return "looped";
			}

			//if x or y is out of bounds, then guard must have left the area
			if (x > bounds || y > bounds || x < 0 || y < 0) {
				return "leftArea";
			}

			//if not visited yet, add it to list
			visited.add(locationKey);

			//check if obstacle in front of guard, if yes, turn 90 degrees
			while (checkForObstacle()) {
				setGuardFacing(facing + 90);

				//if facing reaches 360, reset it to 0
				if (facing == 360) {
					setGuardFacing(0);
				}
			}
			
			//move Guard
			switch (this.facing) {
				case 0:
					setGuardLoc(x, y + 1);
					break;
				case 90:
					setGuardLoc(x + 1, y);
					break;
				case 180:
					setGuardLoc(x, y - 1);
					break;
				case 270:
					setGuardLoc(x - 1, y);
					break;

			}

			// debug line
			// System.out.println("Guard now at " + Guard.x + "," + Guard.y + " and facing " + Guard.facing);

			return "movedInArea";
		}
	}

	private static int createGridsAndTest() {

		int output = 0;

		//loop through all grid position and add an obstacle if possible
		for (int y = 0; y < Part2.grid.get(0).length; y++) {

			Character[] row = Part2.grid.get(y);

			for (int x = 0; x < row.length; x++) {

				//create a deep clone of Part2.grid for use in each iteration
				List<Character[]> gridDupe = new ArrayList<>();
				for (Character[] rowToClone : Part2.grid) {
					gridDupe.add(rowToClone.clone());
				}

				if (gridDupe.get(y)[x] == '#' || gridDupe.get(y)[x] == '^') {
					continue;
				}

				//add obstacle to specific position
				gridDupe.get(y)[x] = '#';

				output += testGrid(gridDupe);
			}
		}

		return output;
	}

	private static int testGrid(List<Character[]> grid) {

		Guard guard = new Guard(grid);
		String status;

		guard.findGuardLoc();

		while ((status = guard.moveGuard()).equals("movedInArea")) {}

		if (status.equals("looped")){
			return 1;
		}

		return 0;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("6");

		parseInput(lines);

		int output = createGridsAndTest();

		System.out.println(output);
	}
}

package com.jonny.Day06;

import java.util.ArrayList;
import java.util.List;

import com.jonny.Helpers;

public class Part1 {

	private static List<Character[]> grid;

	private static List<Character[]> parseInput(List<String> lines) {

		Part1.grid = new ArrayList<>();

		//read it in starting at the bottom, so that positive y goes up later on
		for (int i = lines.size() - 1; i >= 0; i--) {

			String line = lines.get(i);

			Character[] lineArray = new Character[line.length()];

			for (int j = 0; j < line.length(); j++) {

				lineArray[j] = line.charAt(j);
			}

			Part1.grid.add(lineArray);
		}


		return Part1.grid;
	}

	static class Guard {

		private static int x;
		private static int y;
		private static int facing; //direction is measured in degrees

		private void findGuardLoc() {

			for (int y = 0; y < Part1.grid.get(0).length; y++) {

				Character[] row = Part1.grid.get(y);

				for (int x = 0; x < row.length; x++) {

					if (row[x].equals('^')) {
						System.out.println("Found guard at " + x + " " + y);

						setGuardLoc(x, y);
						setGuardFacing(0);
					}
				}
			}
		}

		private void setGuardLoc(int x, int y) {
			Guard.x = x;
			Guard.y = y;
		}

		private void setGuardFacing(int direction) {
			Guard.facing = direction;
		}

		private boolean checkForObstacle() {
			//returns true if it find an obstacle in front

			Character inFront = 'Q';
			
			try {
				switch (facing) {
					case 0:
						inFront = Part1.grid.get(Guard.y + 1)[Guard.x];
						break;
					case 90:
						inFront = Part1.grid.get(Guard.y)[Guard.x + 1];
						break;
					case 180:
						inFront = Part1.grid.get(Guard.y - 1)[Guard.x];
						break;
					case 270:
						inFront = Part1.grid.get(Guard.y)[Guard.x - 1];
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

		private boolean moveGuard() {

			//check if obstacle in front of guard, if yes, turn 90 degrees
			while (checkForObstacle()) {
				setGuardFacing(Guard.facing + 90);

				//if facing reaches 360, reset it to 0
				if (Guard.facing == 360) {
					setGuardFacing(0);
				}
			}
			
			//set it to X so we know it's visited
			try {
				Part1.grid.get(Guard.y)[Guard.x] = 'X';
			} catch (Exception e) {
				//return false if the guard has left the area
				return false;
			}

			//move Guard
			switch (Guard.facing) {
				case 0:
					setGuardLoc(Guard.x, Guard.y + 1);
					break;
				case 90:
					setGuardLoc(Guard.x + 1, Guard.y);
					break;
				case 180:
					setGuardLoc(Guard.x, Guard.y - 1);
					break;
				case 270:
					setGuardLoc(Guard.x - 1, Guard.y);
					break;

			}

			// debug line
			// System.out.println("Guard now at " + Guard.x + "," + Guard.y + " and facing " + Guard.facing);

			return true;
		}
	}

	private static int countXs() {

		int xCount = 0;

		for (int y = 0; y < Part1.grid.get(0).length; y++) {

			Character[] row = Part1.grid.get(y);

			for (int x = 0; x < row.length; x++) {

				if (row[x].equals('X')) {
					xCount += 1;
				}
			}
		}

		return xCount;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("06");

		parseInput(lines);

		Guard guard = new Guard();

		guard.findGuardLoc();

		while(guard.moveGuard()) {
			// System.out.println("Moved guard!");
		}

		// System.out.println("Guard left area!");

		int output = countXs();

		System.out.println(output);

	}
}

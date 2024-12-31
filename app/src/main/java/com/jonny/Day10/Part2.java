package com.jonny.Day10;

import java.util.*;

import com.jonny.Helpers;

public class Part2 {

	private static List<List<Integer>> heightmap;

	private static int xBound;
	private static int yBound;

	private static void parseInput(List<String> lines) {

		Part2.heightmap = new ArrayList<>();

		//read it in starting at the bottom, so that positive y goes up later on
		for (int i = lines.size() - 1; i >= 0; i--) {

			String line = lines.get(i);

			List<Integer> lineArray = new ArrayList<>();;

			for (int j = 0; j < line.length(); j++) {

				lineArray.add(Integer.parseInt("" + line.charAt(j)));
			}

			Part2.heightmap.add(lineArray);
		}

		Part2.xBound = Part2.heightmap.get(0).size() - 1;
		Part2.yBound = Part2.heightmap.size() - 1;
	}

	private static void findTrailheads() {

		int totalTrailheads = 0;

		for (int y = 0; y < Part2.heightmap.size(); y++) {

			List<Integer> row = Part2.heightmap.get(y);

			for (int x = 0; x < row.size(); x++) {

				if (row.get(x) == 0) {
					List<List<Integer>> ninesReachable = new ArrayList<>();

					System.out.println("Searching for nines at x: " + x + " and y: " + y);
					recurseTrails(x, y, -1, new HashSet<>(), ninesReachable);

					// System.out.println(ninesReachable);
					
					totalTrailheads += ninesReachable.size();
				}
			}
		}

		System.out.println(totalTrailheads);
	}

	private static void recurseTrails(int x, int y, int currentHeight, Set<List<Integer>> visited, List<List<Integer>> ninesReachable) {
		if (x > Part2.xBound || x < 0 || y > Part2.yBound || y < 0) {
			return;
		}

		int heightAtCurrent = Part2.heightmap.get(y).get(x);

		if (heightAtCurrent == 9 && currentHeight == 8) {
			// System.out.println("Found a 9 at x: " + x + " y: " + y);
			ninesReachable.add(List.of(x, y));
			return;
		}


		if (visited.contains(List.of(x, y))) {
			return;
		}

		if (heightAtCurrent != currentHeight + 1) {
			return;
		}

		// System.out.println(x + " " + y + " " + heightAtCurrent);

		visited.add(List.of(x,y));
		
		List<List<Integer>> directions = List.of(
			List.of(0, 1),
			List.of(1, 0),
			List.of(0, -1),
			List.of(-1, 0)
		);

		for (List<Integer> direction : directions) {

			int xDiff = direction.get(0);
			int yDiff = direction.get(1);

			recurseTrails(x + xDiff, y + yDiff, heightAtCurrent, visited, ninesReachable);
		}

		visited.remove(List.of(x,y));
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("10");

		parseInput(lines);

		findTrailheads();
	}
}

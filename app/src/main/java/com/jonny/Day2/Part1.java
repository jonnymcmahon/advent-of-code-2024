package com.jonny.Day2;

import java.util.ArrayList;
import java.util.List;

import com.jonny.Helpers;

public class Part1 {

	private static List<int[]> parseInput(List<String> lines) {

		List<int[]> reports = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++){

			String[] lineArray = lines.get(i).split("\\s+");
			
			int[] numArray = new int[lineArray.length];

			for (int j = 0; j < lineArray.length; j++) {
				
				numArray[j] = Integer.parseInt(lineArray[j]);

			}

			reports.add(numArray);
		}

		return reports;
	}

	private static int findSafeReports(List<int[]> reports) {

		int safeReports = 0;

		for (int i = 0; i < reports.size(); i++) { 
			
			String order = null;
			int[] report = reports.get(i);

			//assign order, and if first two nums are same skip it
			//then check if last number fits asc/desc order
			if (report[0] > report[1]) {

				//order is descending
				order = "desc";

				//check if last number is lower than first, if not, skip it
				if (report[report.length - 1] > report[0]) {
					continue;
				}

			} else if (report[0] < report[1]) { 

				//order is ascending
				order = "asc";

				//check if last number is higher than first, if not, skip it
				if (report[report.length - 1] < report[0]) { 
					continue;
				}

			} else {
				continue;
			}

			boolean safe = true;

			//we should have removed a lot of incorrect reports. now lets check the rest
			for (int j = 1; j < report.length; j++) {

				int diff = Math.abs(report[j] - report[j-1]);

				//if diff is less than 1 or more than 3, break
				if (diff < 1 || diff > 3) {
					safe = false;
					break;
				}
				
				//if order is desc but index - 1 is lower, break
				if (order == "desc" && (report[j-1] <= report[j])) {
					safe = false;
					break;	
				}

				//if order is asc but index - 1 is higher, break
				if (order == "asc" && (report[j-1] >= report[j])) {
					safe = false;
					break;
				}
			}


			if (safe) {

				safeReports += 1;
			}

		}

		return safeReports;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("2");

		List<int[]> puzzleInput = parseInput(lines);

		int output = findSafeReports(puzzleInput);

		System.out.println(output);
	}
}

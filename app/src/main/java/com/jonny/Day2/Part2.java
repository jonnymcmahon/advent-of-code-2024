package com.jonny.Day2;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.jonny.Helpers;

public class Part2 {

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

			if (report[0] > report[report.length - 1]) {
				//order is descending
				order = "desc";

			} else if (report[0] < report[report.length - 1]) { 
				//order is ascending
				order = "asc";

			} else {
				continue;
			}

			Map<String, Integer> result = checkReport(report, order);

			int safe = result.get("safe");
			int problems = result.get("problems");
			int problemIndex = result.get("problemIndex");

			if (safe == 1) {
				safeReports += 1;
			}

			//¯\_(ツ)_/¯
			if (problems > 0) {
				int[] modifiedReport = removeProblemIndex(report, problemIndex);

				Map<String, Integer> modifiedResult = checkReport(modifiedReport, order);
				
				safe = modifiedResult.get("safe");

				if (safe == 1) {
					safeReports += 1;
				}

				if (safe == 0) {
					int[] modifiedReport2 = removeProblemIndex(report, problemIndex - 1);

					Map<String, Integer> modifiedResult2 = checkReport(modifiedReport2, order);

					safe = modifiedResult2.get("safe");

					if (safe == 1) {
						safeReports += 1;
					}

				}
			}
		}
		return safeReports;
	}

	private static Map<String, Integer> checkReport(int[] report, String order) {

		int safe = 1;
		int problems = 0;
		int problemIndex = 0;

		//we should have removed a lot of incorrect reports. now lets check the rest
		for (int j = 1; j < report.length; j++) {

			int diff = Math.abs(report[j] - report[j-1]);

			//if diff is less than 1 or more than 3, break
			if (diff < 1 || diff > 3) {
				safe = 0;
				problems += 1;

				if (problems == 1) {
					problemIndex = j;
				}

				continue;
			}
			
			//if order is desc but index - 1 is lower, break
			if (order == "desc" && (report[j-1] <= report[j])) {
				safe = 0;
				problems += 1;

				if (problems == 1) {
					problemIndex = j;
				}

				continue;
			}

			//if order is asc but index - 1 is higher, break
			if (order == "asc" && (report[j-1] >= report[j])) {
				safe = 0;
				problems += 1;

				if (problems == 1) {
					problemIndex = j;
				}

				continue;
			}
		}

		Map<String, Integer> result = new HashMap<>();

		result.put("safe", safe);
		result.put("problems", problems);
		result.put("problemIndex", problemIndex);

		return result;
	}

	private static int[] removeProblemIndex(int[] report, int problemIndex) {

		int[] newReport = new int[report.length - 1];

		for (int i = 0, j = 0; i < report.length; i++) {

			if (i != problemIndex) {
				newReport[j++] = report[i];
			}
		}

		return newReport;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("2");

		List<int[]> puzzleInput = parseInput(lines);

		int output = findSafeReports(puzzleInput);

		System.out.println(output);
	}
}

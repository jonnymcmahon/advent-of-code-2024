package com.jonny.Day05;

import java.util.*;

import com.jonny.Helpers;

public class Part1 {

	private static List<int[]> rules;
	private static List<List<Integer>> updates;

	private static void parseInput(List<String> lines) {

		Part1.rules = new ArrayList<>();
		Part1.updates = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++){

			if (lines.get(i).contains(String.valueOf('|'))) {

				String[] ruleStrArray = lines.get(i).split("\\|");
				int[] ruleArray = new int[2];

				ruleArray[0] = Integer.parseInt(ruleStrArray[0]);
				ruleArray[1] = Integer.parseInt(ruleStrArray[1]);

				Part1.rules.add(ruleArray);
			}

			if (lines.get(i).contains(String.valueOf(','))) {

				String[] updateStrArray = lines.get(i).split("\\,");
				List<Integer> updateArray = new ArrayList<>();

				for (int j = 0; j < updateStrArray.length; j++) {

					updateArray.add(Integer.parseInt(updateStrArray[j]));
				}

				Part1.updates.add(updateArray);
			}
		}
	}

	private static int checkUpdates() {

		int output = 0;

		//loop through each update and see if it is valid
		for (List<Integer> update : Part1.updates){

			if(isValidUpdate(update, Part1.rules)) {
				int middleIndex = update.size() / 2;
				
				output += update.get(middleIndex);
			}
		}

		return output;
	}

	private static boolean isValidUpdate(List<Integer> update, List<int[]> rules) {

		//loop through each rule and check if it after appears earlier in the array than before, if so then not valid
		for (int[] rule : rules) {

			int before = rule[0];
			int after = rule[1];
			
			if(update.contains(before) && update.contains(after)) {

				if (update.indexOf(before) > update.indexOf(after)) {
					return false;
				}
			}
		}
		
		return true;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("05");

		parseInput(lines);

		int output = checkUpdates();

		System.out.println(output);
	}
}

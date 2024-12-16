package com.jonny.Day5;

import java.util.*;

import com.jonny.Helpers;

public class Part2 {

	private static List<int[]> rules;
	private static List<List<Integer>> updates;

	private static void parseInput(List<String> lines) {

		Part2.rules = new ArrayList<>();
		Part2.updates = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++){

			if (lines.get(i).contains(String.valueOf('|'))) {

				String[] ruleStrArray = lines.get(i).split("\\|");
				int[] ruleArray = new int[2];

				ruleArray[0] = Integer.parseInt(ruleStrArray[0]);
				ruleArray[1] = Integer.parseInt(ruleStrArray[1]);

				Part2.rules.add(ruleArray);
			}

			if (lines.get(i).contains(String.valueOf(','))) {

				String[] updateStrArray = lines.get(i).split("\\,");
				List<Integer> updateArray = new ArrayList<>();

				for (int j = 0; j < updateStrArray.length; j++) {

					updateArray.add(Integer.parseInt(updateStrArray[j]));
				}

				Part2.updates.add(updateArray);
			}
		}
	}

	private static int checkUpdates() {

		int output = 0;
		boolean wasIncorrect = false;

		//loop through each update and see if it is valid
		for (List<Integer> update : Part2.updates){

			while(!isValidUpdate(update, Part2.rules)) {

				//if it isn't valid, mark it as incorrect so we can count it for the output
				wasIncorrect = true;

				//rearrange it until we get the correct order, who cares about time complexity anyway
				update = rearrangeUpdate(update, Part2.rules);
				
			}

			//if update began as incorrect, count it towards output
			if(wasIncorrect) {

				int middleIndex = update.size() / 2;

				output += update.get(middleIndex);
			}

			wasIncorrect = false;
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


	private static List<Integer> rearrangeUpdate(List<Integer> update, List<int[]> rules) {

		//loop through each rule and check if it after appears earlier in the array than before, if so rearrange the array and try again
		for (int[] rule : rules) {

			int before = rule[0];
			int after = rule[1];
			
			if(update.contains(before) && update.contains(after)) {

				while (update.indexOf(before) > update.indexOf(after)) {

					//if number is in wrong position, move it 1 index back in the List and try again
					int beforeIndex = update.indexOf(before);

					Collections.swap(update, beforeIndex, beforeIndex - 1);
				}
			}
		}

		return update;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("05");

		parseInput(lines);

		int output = checkUpdates();

		System.out.println(output);
	}
}

package com.jonny.Day01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.jonny.Helpers;

public class Part2 {

	private static List<List<Integer>> parseInput(List<String> lines) {

		List<Integer> leftList = new ArrayList<>();
		List<Integer> rightList = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++){

			String[] lineArray = lines.get(i).split("\\s+");

			leftList.add(Integer.parseInt(lineArray[0]));
			rightList.add(Integer.parseInt(lineArray[1]));
		}

		List<List<Integer>> puzzleInput = new ArrayList<>();

		Collections.sort(leftList);
		Collections.sort(rightList);

		puzzleInput.add(leftList);
		puzzleInput.add(rightList);

		return puzzleInput;
	}

	private static Map<Integer, Integer> findCountOfLeftListInRightList(List<List<Integer>> puzzleInput) {

		Map<Integer, Integer> foundInBothLists = new HashMap<>();

		for (int i = 0; i < puzzleInput.get(0).size(); i++) {

			int leftNumber = puzzleInput.get(0).get(i);

			int count = 0;

			for (int j = 0; j < puzzleInput.get(0).size(); j++) {
				
				int rightNumber = puzzleInput.get(1).get(j);	
				
				if (leftNumber == rightNumber) {
					count += 1;
				}
			}

			if (count != 0) {
				foundInBothLists.put(leftNumber, count);
			}
			
		}

		return foundInBothLists;
	}

	private static int calculateSimilarityScore(Map<Integer, Integer> foundInBothLists) {

		int similarityScore = 0;

		for (Map.Entry<Integer, Integer> entry : foundInBothLists.entrySet()) {

			similarityScore += (entry.getKey() * entry.getValue());
		}

		return similarityScore;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("01");

		List<List<Integer>> puzzleInput = parseInput(lines);

		Map<Integer, Integer> foundInBostLists = findCountOfLeftListInRightList(puzzleInput);

		int output =  calculateSimilarityScore(foundInBostLists);

		System.out.println(output);
	}
}

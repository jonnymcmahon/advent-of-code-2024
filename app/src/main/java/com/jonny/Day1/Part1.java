package com.jonny.Day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jonny.Helpers;

public class Part1 {

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

	private static int compareList(List<List<Integer>> puzzleInput) {

		int output = 0;

		for (int i = 0; i < puzzleInput.get(0).size(); i++){
			
			int num1 = puzzleInput.get(0).get(i);
			int num2 = puzzleInput.get(1).get(i);

			int diff = Math.abs(num1 - num2);

			output += diff;
		}

		return output;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("1");

		List<List<Integer>> puzzleInput = parseInput(lines);

		int output = compareList(puzzleInput);

		System.out.println(output);
	}
}

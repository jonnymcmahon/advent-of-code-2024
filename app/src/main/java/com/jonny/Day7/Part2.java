package com.jonny.Day7;

import java.util.*;

import com.jonny.Helpers;

public class Part2 {

	private static Map<Long, List<Long>> equations;
	private static Map<Integer, List<List<Character>>> operatorCombinations = new HashMap<>();

	private static void parseInput(List<String> lines) {

		Part2.equations = new HashMap<>();

		for (String inputLine : lines){

			//remove : and split by spaces
			String[] line = inputLine.replace(":", "").split(" ");

			Long result = Long.parseLong(line[0]);

			List<Long> numbers = new ArrayList<>();

			for (int j = 1; j < line.length; j++) {

				numbers.add(Long.parseLong(line[j]));
			}

			Part2.equations.put(result, numbers);
		}
	}

	private static List<List<Character>> getCombinations(int length) {

		//if we've already calculated the combinations, return it
		if (operatorCombinations.containsKey(length)) {
			return operatorCombinations.get(length);
		}

		//if not, generate and return
		List<Character> operators = List.of('+', '*', '|');

		List<List<Character>> result = new ArrayList<>();

		//recursion func
		generateCombinationsRecursion(operators, length, new ArrayList<>(), result);

		operatorCombinations.put(length, result);

		return operatorCombinations.get(length);
	}

	private static void generateCombinationsRecursion(List<Character> operators, int length, List<Character> current, List<List<Character>> result) {

		//base case is when length of array is desired length
		if (current.size() == length) {
			result.add(new ArrayList<>(current));
			return;
		}

		//recurse through each operator until it is long enough and then backtrack to get all combinations
		for (Character operator : operators) {
			current.add(operator);
			generateCombinationsRecursion(operators, length, current, result);
			current.remove(current.size() - 1);
		}

	}

	private static void checkEquations() {

		Long totalCalibResult = 0L;

		for (Map.Entry<Long, List<Long>> entry : Part2.equations.entrySet()) {

			Long result = entry.getKey();
			List<Long> numbers = entry.getValue();

			int numOfOperatorsNeeded = numbers.size() - 1;

			List<List<Character>> operatorCombs = getCombinations(numOfOperatorsNeeded);

			Long equationResult = 0L;

			for (List<Character> operators : operatorCombs) { 

				equationResult = getEquationResult(operators, numbers, result);

				if(Long.compare(equationResult, result) == 0) {
					totalCalibResult += result;
					break;
				}
			}
		}

		System.out.println(totalCalibResult);
	}

	private static Long getEquationResult(List<Character> operators, List<Long> numbers, Long expected) {

		//set starting number
		Long total = numbers.get(0);

		//loop through numbers and do specified operator for each from list
		for (int i = 1, j = 0; i < numbers.size(); i++, j++) {

			//if total number goes above the expected result, return as it can't be correct
			if (total > expected) {
				return 0L;
			}

			switch(operators.get(j)) {
				case '+':
					total += numbers.get(i);	
					break;
				case '*':
					total = total * numbers.get(i);
					break;
				case '|':
					String totalStr = String.valueOf(total);
					String nextNumStr = String.valueOf(numbers.get(i));
					String combinedStr = totalStr + nextNumStr;
					total = Long.parseLong(combinedStr);
					break;
			}
		}

		return total;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("7");

		parseInput(lines);

		checkEquations();
	}
}

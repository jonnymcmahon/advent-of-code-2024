package com.jonny.Day03;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

import com.jonny.Helpers;

public class Part2 {

	private static String removeDonts(List<String> lines) {

		String puzzleInput = "";

		for (int i = 0; i < lines.size(); i++) {

			puzzleInput = puzzleInput + lines.get(i);
		}

		String regexDonts = "(?<=don't\\(\\)).*?(?=do\\(\\)|don't\\(\\)|$)";

		Pattern patternDonts = Pattern.compile(regexDonts);

		Matcher matcherDonts = patternDonts.matcher(puzzleInput);

		while (matcherDonts.find()) {

			puzzleInput = puzzleInput.replace(matcherDonts.group(), "");
		}

		return puzzleInput;
	}

	private static List<String> parseInput(String puzzleInput) {

		String regex = "mul\\(\\d+,\\d+\\)";

		Pattern pattern = Pattern.compile(regex);

		List<String> muls = new ArrayList<>();

		Matcher matcher = pattern.matcher(puzzleInput);

		while (matcher.find()) {
			muls.add(matcher.group());
		}

		return muls;
	}

	private static int doMultiplication(List<String> puzzleInput) {


		int output = 0;

		String regexNum1 = "(?<=\\()\\d+";
		String regexNum2 = "(?<=\\,)\\d+";

		Pattern patternNum1 = Pattern.compile(regexNum1);
		Pattern patternNum2 = Pattern.compile(regexNum2);

		for (int i = 0; i < puzzleInput.size(); i++){
			
			int num1 = 0;
			int num2 = 0;

			Matcher matcherNum1 = patternNum1.matcher(puzzleInput.get(i));
			Matcher matcherNum2 = patternNum2.matcher(puzzleInput.get(i));
			
			while (matcherNum1.find()) {
				num1 = Integer.parseInt(matcherNum1.group());
			}

			while (matcherNum2.find()) {
				num2 = Integer.parseInt(matcherNum2.group());
			}

			int multiplied = num1 * num2;

			output += multiplied;
		}

		return output;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("03");

		String doSections = removeDonts(lines);

		List<String> puzzleInput = parseInput(doSections);

		int output = doMultiplication(puzzleInput);

		System.out.println(output);
	}
}

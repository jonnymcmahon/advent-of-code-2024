package com.jonny.Day11;

import java.util.*;
import java.math.BigInteger;

import com.jonny.Helpers;

public class Part1 {

	private static List<BigInteger> parseInput(List<String> lines) {

		List<BigInteger> stones = new ArrayList<>();

		String[] lineArray = lines.get(0).split("\\s+");

		for (int i = 0; i < lineArray.length; i++) {

			BigInteger stone = new BigInteger(lineArray[i]);

			stones.add(stone);
		}

		return stones;
	}

	private static void doBlinks(List<BigInteger> stones){ 
		
		int blinksToDo = 25;

		for (int blinks = 0; blinks < blinksToDo; blinks++) {
			
			stones = doBlink(stones);

		}

		System.out.println(stones.size());

	}

	private static List<BigInteger> doBlink(List<BigInteger> stones) {

		List<BigInteger> stonesAfterBlink = new ArrayList<>();

		for (int i = 0; i < stones.size(); i++) {

			BigInteger stone = stones.get(i);

			if (stone.compareTo(BigInteger.ZERO) == 0) {
				stonesAfterBlink.add(BigInteger.valueOf(1));
				continue;
			}

			String stoneAsString = String.valueOf(stone);
			int stoneNumberLength = stoneAsString.length();

			if (stoneNumberLength % 2 == 0) {

				String string1 = stoneAsString.substring(0, (stoneNumberLength / 2));
				String string2 = stoneAsString.substring((stoneNumberLength / 2), stoneNumberLength);

				BigInteger num1 = BigInteger.valueOf(0);
				BigInteger num2 = BigInteger.valueOf(0);

				if (string1 != "0") {
					num1 = new BigInteger(string1);
				}

				if (string2 != "0") {
					num2 = new BigInteger(string2);
				}

				stonesAfterBlink.add(num1);
				stonesAfterBlink.add(num2);
				continue;
			}

			stonesAfterBlink.add(stone.multiply(BigInteger.valueOf(2024)));
		}

		return stonesAfterBlink;

	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("11");

		List<BigInteger> stones = parseInput(lines);

		doBlinks(stones);

		// System.out.println(output);
	}
}

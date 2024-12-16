package com.jonny.Day4;

import java.util.ArrayList;
import java.util.List;

import com.jonny.Helpers;

public class Part2 {

	private static List<Character[]> puzzleInput;

	private static List<Character[]> parseInput(List<String> lines) {

		Part2.puzzleInput = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++) {

			String line = lines.get(i);

			Character[] lineArray = new Character[line.length()];

			for (int j = 0; j < line.length(); j++) {

				lineArray[j] = line.charAt(j);
			}

			Part2.puzzleInput.add(lineArray);
		}

		return Part2.puzzleInput;
	}

	private static int doSolution() {

		//----> = +x
		//<---- = -x
		//^^^^^ = +y
		//VVVVV = -y
		
		int xmasFound = 0;

		boolean diagonal1 = false;
		boolean diagonal2 = false;

		for (int y = 0; y < Part2.puzzleInput.size(); y++) {

			for (int x = 0; x < Part2.puzzleInput.get(y).length; x++) { 

				if (Part2.puzzleInput.get(y)[x] == 'A'){

					diagonal1 = checkTopLeftToBottomRightDiagonal(x, y);	

					diagonal2 = checkBottomLeftToTopRightDiagonal(x, y);

					if (diagonal1 && diagonal2) {
						xmasFound += 1;
					}

					diagonal1 = false;
					diagonal2 = false;
				}
			}
		}
		
		return xmasFound;
	}

	private static Character getDiagonal(int originX, int originY, int offset, String mode) {

		//default
		Character returnChar = 'Z';

		try {
			switch (mode) {
				case "topLeft":
					returnChar = Part2.puzzleInput.get(originY - offset)[originX - offset];
					break;
				case "bottomLeft":
					returnChar = Part2.puzzleInput.get(originY + offset)[originX - offset];
					break;
				case "topRight":
					returnChar = Part2.puzzleInput.get(originY - offset)[originX + offset];
					break;
				case "bottomRight":
					returnChar = Part2.puzzleInput.get(originY + offset)[originX + offset];
					break;
			}
		} catch (Exception e) {
			return 'E';
		}

		return returnChar;
	}

	private static boolean checkTopLeftToBottomRightDiagonal(int originX, int originY) {

		Character char1 = getDiagonal(originX, originY, 1, "topLeft");
		Character char2 = 'A';
		Character char3 = getDiagonal(originX, originY, 1, "bottomRight");

		StringBuilder sb = new StringBuilder();
		sb.append(char1).append(char2).append(char3);

		if (sb.toString().equals("MAS") || sb.toString().equals("SAM")) {
			return true;
		}

		return false;
	}

	private static boolean checkBottomLeftToTopRightDiagonal(int originX, int originY) {

		Character char1 = getDiagonal(originX, originY, 1, "bottomLeft");
		Character char2 = 'A';
		Character char3 = getDiagonal(originX, originY, 1, "topRight");

		StringBuilder sb = new StringBuilder();
		sb.append(char1).append(char2).append(char3);

		if (sb.toString().equals("MAS") || sb.toString().equals("SAM")) {
			return true;
		}

		return false;
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("04");

		parseInput(lines);

		int xmasFound = doSolution();

		System.out.println(xmasFound);
	}
}

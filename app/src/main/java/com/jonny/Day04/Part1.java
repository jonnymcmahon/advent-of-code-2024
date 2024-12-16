package com.jonny.Day4;

import java.util.ArrayList;
import java.util.List;

import com.jonny.Helpers;

public class Part1 {

	private static List<Character[]> puzzleInput;

	private static List<Character[]> parseInput(List<String> lines) {

		Part1.puzzleInput = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++) {

			String line = lines.get(i);

			Character[] lineArray = new Character[line.length()];

			for (int j = 0; j < line.length(); j++) {

				lineArray[j] = line.charAt(j);
			}

			Part1.puzzleInput.add(lineArray);
		}

		return Part1.puzzleInput;
	}

	private static int doSolution() {

		//----> = +x
		//<---- = -x
		//^^^^^ = +y
		//VVVVV = -y
		
		int xmasFound = 0;

		for (int y = 0; y < Part1.puzzleInput.size(); y++) {

			for (int x = 0; x < Part1.puzzleInput.get(y).length; x++) { 

				if (Part1.puzzleInput.get(y)[x] == 'X'){

					xmasFound += checkUp(x, y);
					xmasFound += checkDown(x, y);

					xmasFound += checkLeft(x, y);
					xmasFound += checkRight(x, y);

					xmasFound += checkTopLeft(x, y);
					xmasFound += checkTopRight(x, y);
					xmasFound += checkBottomLeft(x, y);
					xmasFound += checkBottomRight(x, y);
				}
			}
		}
		
		return xmasFound;
	}

	private static Character getVertical(int originX, int originY, int offset) {
		//-ve offset = down, +ve offset = up

		//default
		Character returnChar = 'Z';

		try {
			returnChar = Part1.puzzleInput.get(originY - offset)[originX];
		} catch (Exception e) {
			return 'E';
		}

		return returnChar;
	}

	private static Character getHorizontal(int originX, int originY, int offset) {

		//default
		Character returnChar = 'Z';

		try {
			returnChar = Part1.puzzleInput.get(originY)[originX + offset];
		} catch (Exception e) {
			return 'E';
		}

		return returnChar;
	}

	private static Character getDiagonal(int originX, int originY, int offset, String mode) {

		//default
		Character returnChar = 'Z';

		try {
			switch (mode) {
				case "topLeft":
					returnChar = Part1.puzzleInput.get(originY - offset)[originX - offset];
					break;
				case "bottomLeft":
					returnChar = Part1.puzzleInput.get(originY + offset)[originX - offset];
					break;
				case "topRight":
					returnChar = Part1.puzzleInput.get(originY - offset)[originX + offset];
					break;
				case "bottomRight":
					returnChar = Part1.puzzleInput.get(originY + offset)[originX + offset];
					break;
			}
		} catch (Exception e) {
			return 'E';
		}

		return returnChar;
	}

	private static int checkUp(int originX, int originY) {

		Character char2 = getVertical(originX, originY, 1);
		Character char3 = getVertical(originX, originY, 2);
		Character char4 = getVertical(originX, originY, 3);

		StringBuilder sb = new StringBuilder();
		sb.append('X').append(char2).append(char3).append(char4);

		if (sb.toString().equals("XMAS")) {
			return 1;
		}
		
		return 0;
	}

	private static int checkDown(int originX, int originY) {

		Character char2 = getVertical(originX, originY, -1);
		Character char3 = getVertical(originX, originY, -2);
		Character char4 = getVertical(originX, originY, -3);

		StringBuilder sb = new StringBuilder();
		sb.append('X').append(char2).append(char3).append(char4);

		if (sb.toString().equals("XMAS")) {
			return 1;
		}
		
		return 0;
	}

	private static int checkLeft(int originX, int originY) {

		Character char2 = getHorizontal(originX, originY, -1);
		Character char3 = getHorizontal(originX, originY, -2);
		Character char4 = getHorizontal(originX, originY, -3);

		StringBuilder sb = new StringBuilder();
		sb.append('X').append(char2).append(char3).append(char4);

		if (sb.toString().equals("XMAS")) {
			return 1;
		}
		
		return 0;
	}

	private static int checkRight(int originX, int originY) {

		Character char2 = getHorizontal(originX, originY, 1);
		Character char3 = getHorizontal(originX, originY, 2);
		Character char4 = getHorizontal(originX, originY, 3);

		StringBuilder sb = new StringBuilder();
		sb.append('X').append(char2).append(char3).append(char4);

		if (sb.toString().equals("XMAS")) {
			return 1;
		}
		
		return 0;
	}

	private static int checkTopLeft(int originX, int originY) {

		String mode = "topLeft";

		Character char2 = getDiagonal(originX, originY, 1, mode);
		Character char3 = getDiagonal(originX, originY, 2, mode);
		Character char4 = getDiagonal(originX, originY, 3, mode);

		StringBuilder sb = new StringBuilder();
		sb.append('X').append(char2).append(char3).append(char4);

		if (sb.toString().equals("XMAS")) {
			return 1;
		}
		
		return 0;
	}

	private static int checkTopRight(int originX, int originY) {

		String mode = "topRight";

		Character char2 = getDiagonal(originX, originY, 1, mode);
		Character char3 = getDiagonal(originX, originY, 2, mode);
		Character char4 = getDiagonal(originX, originY, 3, mode);

		StringBuilder sb = new StringBuilder();
		sb.append('X').append(char2).append(char3).append(char4);

		if (sb.toString().equals("XMAS")) {
			return 1;
		}
		
		return 0;
	}

	private static int checkBottomLeft(int originX, int originY) {

		String mode = "bottomLeft";

		Character char2 = getDiagonal(originX, originY, 1, mode);
		Character char3 = getDiagonal(originX, originY, 2, mode);
		Character char4 = getDiagonal(originX, originY, 3, mode);

		StringBuilder sb = new StringBuilder();
		sb.append('X').append(char2).append(char3).append(char4);

		if (sb.toString().equals("XMAS")) {
			return 1;
		}
		
		return 0;
	}

	private static int checkBottomRight(int originX, int originY) {

		String mode = "bottomRight";

		Character char2 = getDiagonal(originX, originY, 1, mode);
		Character char3 = getDiagonal(originX, originY, 2, mode);
		Character char4 = getDiagonal(originX, originY, 3, mode);

		StringBuilder sb = new StringBuilder();
		sb.append('X').append(char2).append(char3).append(char4);

		if (sb.toString().equals("XMAS")) {
			return 1;
		}
		
		return 0;
	}


	

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("04");

		parseInput(lines);

		int xmasFound = doSolution();

		System.out.println(xmasFound);
	}
}

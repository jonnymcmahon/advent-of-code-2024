package com.jonny.Day9;

import java.util.*;

import com.jonny.Helpers;

public class Part1 {

	private static List<Integer> parseInput(List<String> lines) {

		List<Integer> diskMap = new ArrayList<>();

		String line = lines.get(0);

		for (int i = 0; i < line.length(); i++) {

			diskMap.add(Character.getNumericValue(line.charAt(i)));
		}

		return diskMap;
	}

	private static List<Integer> expandDiskMap(List<Integer> diskMap) {

		List<Integer> expandedDiskMap = new ArrayList<>();

		int id = 0;

		for (int i = 0; i < diskMap.size(); i++) {

			int blockLength = diskMap.get(i);

			//if remainder = 1, free space, if remainder 0, block of IDs
			int type = i % 2;

			switch (type) {
				case 0:
					for (int j = 0; j < blockLength; j++) {
						expandedDiskMap.add(id);
					}
					id += 1;
					break;

				case 1:
					for (int j = 0; j < blockLength; j++) {
						expandedDiskMap.add(null);
					}
					break;
			}
		}

		return expandedDiskMap;
	}

	private static List<Integer> compressDiskMap(List<Integer> expandedDiskMap) {

		//loop through expandedDiskMap with two counters, one moving forwards and one backwards
		for (int i = 0, j = expandedDiskMap.size() - 1; i < expandedDiskMap.size(); i++) {

			//if j (index) catches up or is equal to i (index) we're done
			if (j <= i){
				break;
			}

			//if index = null, swap it with last non-null index in the list
			if (expandedDiskMap.get(i) == null) {

				while(j > i && expandedDiskMap.get(j) == null) {

					j -= 1;
				}

				expandedDiskMap.set(i, expandedDiskMap.get(j));

				expandedDiskMap.set(j, null);

				j -= 1;

			}
		}

		return expandedDiskMap;
	}

	private static void generateChecksum(List<Integer> compressedDiskMap) {

		Long checksum = 0L;

		for (int i = 0; i < compressedDiskMap.size(); i++) {

			if (compressedDiskMap.get(i) != null) {
				checksum += (compressedDiskMap.get(i) * i);	
			}
		}

		System.out.println(checksum);
	}

	public static void main() {

		Helpers helper = new Helpers();

		List<String> lines = helper.readInput("09");

		List<Integer> diskMap = parseInput(lines);

		List<Integer> expandedDiskMap = expandDiskMap(diskMap);

		List<Integer> compressedDiskMap = compressDiskMap(expandedDiskMap);

		generateChecksum(compressedDiskMap);
	}
}

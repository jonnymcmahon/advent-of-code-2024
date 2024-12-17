package com.jonny.Day09;

import java.util.*;

import com.jonny.Helpers;

public class Part2 {

	//Key = fileID, List[0] = length, List[1] = startingIndex
	private static Map<Integer, List<Integer>> fileIdMap;

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

		Part2.fileIdMap = new LinkedHashMap<>();

		int id = 0;

		for (int i = 0; i < diskMap.size(); i++) {

			int blockLength = diskMap.get(i);

			//if remainder = 1, free space, if remainder 0, block of IDs
			int type = i % 2;

			switch (type) {
				case 0:
					int startingIndex = expandedDiskMap.size();

					for (int j = 0; j < blockLength; j++) {
						expandedDiskMap.add(id);
					}

					//add fileId info to a hashmap recording information about block
					Part2.fileIdMap.put(id, List.of(blockLength, startingIndex));

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
	
		//reverse hashmap
		List<Map.Entry<Integer, List<Integer>>> fileIdMapReversed = new ArrayList<>(Part2.fileIdMap.entrySet());
		Collections.reverse(fileIdMapReversed);

		for (Map.Entry<Integer, List<Integer>> fileIdEntry : fileIdMapReversed) {

			//unpack things
			int fileId = fileIdEntry.getKey();
			List<Integer> values = fileIdEntry.getValue();

			int blockLength = values.get(0);
			int startingIndex = values.get(1);

			//loop through expandedDiskMap to find a null block long enough 
			for (int i = 0; i < expandedDiskMap.size(); i++) {

				//if we go past the start index of the fileId, we can't move it
				if (i > startingIndex) {
					break;
				}

				//if we find a null, find its length
				int nullBlockLength = 0;
				int nullBlockStartingIndex = i;
				while (expandedDiskMap.get(i) == null) {
					
					nullBlockLength += 1;	
					i += 1;
				}

				//if there is a null block found that can fit the fileId in, put it in and then break
				if (nullBlockLength >= blockLength) {
					
					//add fileId to gap and set its original location to null
					for (int j = 0; j < blockLength; j++){
						expandedDiskMap.set(nullBlockStartingIndex, fileId);
						expandedDiskMap.set(startingIndex, null);
						nullBlockStartingIndex += 1;
						startingIndex += 1;
					}
					break;
				}

			}

		}
		/*
		 * find largest unmoved ID in list
		 *	find first record of fileID
		 *	move backwards until you find the first one
		 *	record its length = ? && record its starting index
		 *
		 * find a spot long enough to fit it 
		 *	
		 *	go through list find a null > check how many come after it
		 *	if index of null found reaches  >= starting index of block > break
		 *	if tips over length needed, move fileID
		 *
		 * if there are no spots, leave it where it is
		 *
		 * add ID to fileIDsMoved list
		 *
		 * each for each unmoved ID, search through the list from the beginning
		 */

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

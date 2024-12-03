package com.jonny;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

public class Helpers {

	public List<String> readInput(String day) {

		Path path = Path.of("src/main/resources/day" + day + ".txt");	
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

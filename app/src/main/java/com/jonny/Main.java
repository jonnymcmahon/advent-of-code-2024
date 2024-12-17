package com.jonny;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Select calendar day: ");

		String day = scanner.nextLine();

		//add a leading 0 if day package required is < 10
		if (day.length() == 1){ 
			day = "0" + day;
		}

		System.out.print("Select puzzle part: ");

		String part = scanner.nextLine();


		long startTime = System.nanoTime();

		try {
			String className = "com.jonny.Day" + day + ".Part" + part;
			Class<?> clazz = Class.forName(className);
			Object obj = clazz.getDeclaredConstructor().newInstance();

			clazz.getMethod("main").invoke(obj);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		long endTime = System.nanoTime();

		long duration = endTime - startTime;
		System.out.println("Execution time: " + (duration / 1_000_000) + " ms");

		scanner.close();
	}
}

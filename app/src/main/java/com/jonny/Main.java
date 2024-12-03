package com.jonny;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Select calendar day: ");

		String day = scanner.nextLine();

		System.out.print("Select puzzle part: ");

		String part = scanner.nextLine();

		try {
			String className = "com.jonny.Day" + day + ".Part" + part;
			Class<?> clazz = Class.forName(className);
			Object obj = clazz.getDeclaredConstructor().newInstance();

			clazz.getMethod("main").invoke(obj);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		scanner.close();
	}
}

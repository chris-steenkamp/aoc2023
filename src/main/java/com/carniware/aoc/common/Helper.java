package com.carniware.aoc.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class Helper {
	public static List<String> readFile(String fileName) {
		var result = new ArrayList<String>();
		try {
			result.addAll( Files.readAllLines(Paths.get(fileName)));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return result;
	}
}
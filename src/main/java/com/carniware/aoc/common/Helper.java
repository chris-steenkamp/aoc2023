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
			result.addAll(Files.readAllLines(Paths.get(fileName)));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return result;
	}

	public static List<String> transpose(List<String> inputRows) {
		List<String> transposed = new ArrayList<>();

		int rows = inputRows.getFirst().length();
		int cols = inputRows.size();
		for (var i = 0; i < rows; ++i) {
			StringBuilder sb = new StringBuilder();
			for (var j = cols - 1; j >= 0; --j) {
				sb.append(inputRows.get(j).charAt(i));
			}

			transposed.add(sb.toString());
		}

		return transposed;
	}

	public static int hammingDistance(String input1, String input2) {
		if (input1.length() != input2.length()) {
			// Can't compute the hamming distance of unequal length strings
			return -1;
		}

		var distance = 0;
		for (var i = 0; i < input1.length(); ++i) {
			if (input1.charAt(i) != input2.charAt(i)) {
				++distance;
			}
		}

		return distance;
	}
}
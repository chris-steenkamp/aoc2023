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

	public static List<String> rotate(List<String> inputRows) {
		return rotate(inputRows, false);
	}

	public static List<String> rotate(List<String> inputRows, Boolean reverse) {
		List<String> transposed = new ArrayList<>();

		if (reverse) {
			int rows = inputRows.getFirst().length();
			int cols = inputRows.size();
			for (var j = rows - 1; j >= 0; --j) {
				StringBuilder sb = new StringBuilder();
				for (var i = 0; i < cols; ++i) {
					sb.append(inputRows.get(i).charAt(j));
				}

				transposed.add(sb.toString());
			}
		} else {
			int rows = inputRows.getFirst().length();
			int cols = inputRows.size();
			for (var i = 0; i < rows; ++i) {
				StringBuilder sb = new StringBuilder();
				for (var j = cols - 1; j >= 0; --j) {
					sb.append(inputRows.get(j).charAt(i));
				}

				transposed.add(sb.toString());
			}
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
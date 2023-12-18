package com.carniware.aoc.common;

import static java.util.Map.entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Helper {
	public interface P2 {
		int x();

		int y();
	}

	public record Point2(int x, int y) implements P2 {
	}

	public static Boolean withinBounds(P2 p, int x, int y) {
		return withinBounds(p, 0, 0, x, y);
	}

	public static Boolean withinBounds(P2 p, int x1, int y1, int x2, int y2) {
		if (p.x() < x1 || p.x() >= x2 || p.y() < y1 || p.y() >= y2) {
			return false;
		}

		return true;
	}

	public static long getManhattanDistance(Point2 p1, Point2 p2) {
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
	}

	public static class CardinalDirection {
		public enum HEADING {
			N, // heading south to north
			W, // heading east to west
			S, // heading north to south
			E // heading west to east
		}

		static Map<HEADING, HEADING> previousDirection = Map.ofEntries(
				entry(HEADING.N, HEADING.W),
				entry(HEADING.W, HEADING.S),
				entry(HEADING.S, HEADING.E),
				entry(HEADING.E, HEADING.N));

		static Map<HEADING, HEADING> nextDirection = Map.ofEntries(
				entry(HEADING.N, HEADING.E),
				entry(HEADING.E, HEADING.S),
				entry(HEADING.S, HEADING.W),
				entry(HEADING.W, HEADING.N));

		public static HEADING turnLeft(HEADING heading) {
			return previousDirection.get(heading);
		}

		public static HEADING turnRight(HEADING heading) {
			return nextDirection.get(heading);
		}
	}

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
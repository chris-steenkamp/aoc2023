package com.carniware.aoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carniware.aoc.day02.Day02;

@SpringBootApplication
public class AocApplication {
	public static void main(String[] args) {
		SpringApplication.run(AocApplication.class, args);

		var day = new Day02();
		day.runPart1();
		day.runPart2();
	}
}

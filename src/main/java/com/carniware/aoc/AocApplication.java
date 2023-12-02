package com.carniware.aoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.carniware.aoc.day02.Day02;

@SpringBootApplication
@ComponentScan("com.carniware.aoc.days")
public class AocApplication {
	public static void main(String[] args) {
		SpringApplication.run(AocApplication.class, args);

		var day = new Day02();
		System.out.println(day.getPart1Result());
		System.out.println(day.getPart2Result());
	}
}

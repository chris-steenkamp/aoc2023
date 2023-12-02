package com.carniware.aoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.carniware.aoc.common.AoCDay;
import com.carniware.aoc.day01.Day01;

@SpringBootApplication
@ComponentScan("com.carniware.aoc.days")
public class AocApplication {
	public static void main(String[] args) {
		SpringApplication.run(AocApplication.class, args);

		AoCDay day = new Day01();
		System.out.println(day.getPart1Result());
		System.out.println(day.getPart2Result());
	}
}

package com.carniware.aoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carniware.aoc.day01.Go;

@SpringBootApplication
public class AocApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(AocApplication.class, args);

		var day = context.getBean(Go.class);

		System.out.println(day.getResult());
	}

}

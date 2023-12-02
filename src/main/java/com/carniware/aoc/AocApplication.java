package com.carniware.aoc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carniware.aoc.common.AoCDay;

@SpringBootApplication
public class AocApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AocApplication.class, args);
	}

    @Autowired
    private List<AoCDay> daysImplemented;

    @Override
    public void run(String... args) throws Exception {
        // Run a specific day if is specified as an argument, otherwise run the latest implemented day.
        // Index position 0 is the template, so the actual days map 1:1 to their indices in the list.
        int specificDay = args.length > 0 ? Integer.parseInt(args[0]) : daysImplemented.size() - 1;
		var day = daysImplemented.get(specificDay);
		day.runPart1();
		day.runPart2();
    }
}

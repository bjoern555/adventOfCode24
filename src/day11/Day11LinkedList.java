package src.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Day11LinkedList {
    static LinkedList<Long> stones;

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        for (int i = 0; i < 25; i++) {
            System.out.println(i);
            blink();
        }
        System.out.println(stones.size());
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/input-day11");

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stones = new LinkedList<>(Arrays.stream(line.split(" ")).map(Long::parseLong).toList());
            }
        }
    }

    private static void blink() {
        ListIterator<Long> iterator = stones.listIterator();
        while (iterator.hasNext()) {
            Long stone = iterator.next();
            int digits = stone.toString().length();

            if (stone == 0) {
                iterator.set(1L);
            } else if (digits % 2 == 0) {
                long firsthalf = (long) (stone / Math.pow(10, digits / 2));
                long secondhalf = (long) (stone - firsthalf * Math.pow(10, digits / 2));

                iterator.set(firsthalf);
                iterator.add(secondhalf);
            } else {
                iterator.set(stone * 2024);
            }
        }
    }
}
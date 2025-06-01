package src.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day11 {
    static List<Long> stones = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        for (int i = 0; i < 75; i++) {
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
                stones = new ArrayList<>(Arrays.stream(line.split(" ")).map(Long::parseLong).toList());
            }
        }
    }

    private static void blink() {
        for (int i = 0; i < stones.size(); i++) {
            int digits = stones.get(i).toString().length();

            if (stones.get(i) == 0) {
                stones.set(i, 1L);
            } else if (digits % 2 == 0) {
                long firsthalf = (long) (stones.get(i) / Math.pow(10, digits / 2));
                long secondhalf = (long) (stones.get(i) - firsthalf * Math.pow(10, digits / 2));

                stones.set(i, firsthalf);
                stones.add(i + 1, secondhalf);
                i++;
            } else {
                stones.set(i, stones.get(i) * 2024);
            }
        }
    }
}

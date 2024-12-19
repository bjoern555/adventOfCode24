package src.day10;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day10 {
    static int[][] map;

    public static void main(String[] args) throws FileNotFoundException {
        int sum = 0;
        readFile();
        sum += searchTrailheads();
        System.out.println(sum);
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/input-day10");
        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }

        int size = lines.size();
        map = new int[size][size];
        for (int i = 0; i < map.length; i++) {
            map[i] = toIntegerArray(lines.get(i));
        }
    }

    private static int[] toIntegerArray(String line) {
        return line.chars().map(Character::getNumericValue).toArray();
    }

    private static int searchTrailheads() {
        int sum = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                sum += checkTrailsForTrailHeads2(i, j, 0);
            }
        }
        return sum;
    }

    private static int checkTrailsForTrailHeads2(int i, int j, int searchingValue) {
        if (map[i][j] == searchingValue && searchingValue == 9) {
            return 1;
        }

        if (map[i][j] == searchingValue) {
            return ((i - 1 >= 0) ? checkTrailsForTrailHeads2(i - 1, j, searchingValue + 1) : 0)
                    + ((i + 1 < map.length) ? checkTrailsForTrailHeads2(i + 1, j, searchingValue + 1) : 0)
                    + ((j - 1 >= 0) ? checkTrailsForTrailHeads2(i, j - 1, searchingValue + 1) : 0)
                    + ((j + 1 < map.length) ? checkTrailsForTrailHeads2(i, j + 1, searchingValue + 1) : 0);
        } else {
            return 0;
        }
    }

    private static int checkTrailsForTrailHeads1(int i, int j, int searchingValue, Set<Point> endingPoints) {
        if (map[i][j] == searchingValue && searchingValue == 9) {
            Point endingPoint = new Point(i, j);
            if (!endingPoints.contains(endingPoint)) {
                endingPoints.add(new Point(i, j));
                return 1;
            } else {
                return 0;
            }
        }

        if (map[i][j] == searchingValue) {
            return ((i - 1 >= 0) ? checkTrailsForTrailHeads1(i - 1, j, searchingValue + 1, endingPoints) : 0)
                    + ((i + 1 < map.length) ? checkTrailsForTrailHeads1(i + 1, j, searchingValue + 1, endingPoints) : 0)
                    + ((j - 1 >= 0) ? checkTrailsForTrailHeads1(i, j - 1, searchingValue + 1, endingPoints) : 0)
                    + ((j + 1 < map.length) ? checkTrailsForTrailHeads1(i, j + 1, searchingValue + 1, endingPoints) : 0);
        } else {
            return 0;
        }
    }
}

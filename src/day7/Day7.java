package src.day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day7 {
    static Map<Long, List<Long>> equations = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        long sum = checkEquations();
        System.out.println(sum);
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/input-day7");

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(":");
                List<Long> operands = Arrays.stream(values[1].trim().split(" "))
                        .map(Long::parseLong)
                        .toList();
                equations.put(Long.parseLong(values[0]), operands);
            }
        }
    }

    private static long checkEquations() {
        long sum = 0;
        for (Map.Entry<Long, List<Long>> entry : equations.entrySet()) {
            long key = entry.getKey();
            List<Long> values = entry.getValue();
            List<Long> remainingValues = new ArrayList<>(values);
            long startingParameter = remainingValues.removeFirst();
            if (checkEquation(key, remainingValues, startingParameter)) {
                sum += key;
            }
        }
        return sum;
    }

    private static boolean checkEquation(long goalValue, List<Long> values, long currentValue) {
        if (values.isEmpty() || currentValue > goalValue) {
            return currentValue == goalValue;
        }

        List<Long> remainingValues = new ArrayList<>(values);
        long nextParam = remainingValues.removeFirst();
        long concatenated = currentValue * (long) Math.pow(10, Long.toString(Math.abs(nextParam)).length()) + nextParam;
        return checkEquation(goalValue, remainingValues, currentValue * nextParam)
                || checkEquation(goalValue, remainingValues, currentValue + nextParam)
                || checkEquation(goalValue, remainingValues, concatenated);
    }
}

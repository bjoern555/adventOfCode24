package src.day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day5 {
    static Map<Integer, Set<Integer>> pageOrderingRules = new HashMap<>();
    static List<List<Integer>> updates = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        int sum = 0;
        readFile();
        sum += checkUpdates();
        System.out.println(sum);
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/input-day5");
        Scanner scanner = new Scanner(new FileReader(file));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                break;
            }
            List<Integer> ruleValues = Arrays.stream(line.split("\\|"))
                    .map(Integer::parseInt)
                    .toList();
            if (pageOrderingRules.containsKey(ruleValues.getFirst())) {
                pageOrderingRules.get(ruleValues.getFirst()).add(ruleValues.get(1));
            } else {
                pageOrderingRules.put(ruleValues.getFirst(), new HashSet<>(Set.of(ruleValues.get(1))));
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            updates.add(Arrays.stream(line.split(","))
                    .map(Integer::parseInt)
                    .toList());
        }
        scanner.close();
    }

    private static int checkUpdates() {
        int sum = 0;
        for (List<Integer> update : updates) {
            boolean correct = true;
            for (int i = 0; i < update.size() && correct; i++) {
                if (!checkValue(update, i)) {
                    correct = false;
                }
            }
            if (correct) {
                sum += update.get(update.size() / 2);
            }
        }
        return sum;
    }

    private static boolean checkValue(List<Integer> update, int i) {
        for (int j = 0; j < i; j++) {
            if (pageOrderingRules.containsKey(update.get(i)) && pageOrderingRules.get(update.get(i)).contains(update.get(j))) {
                return false;
            }
        }
        return true;
    }
}

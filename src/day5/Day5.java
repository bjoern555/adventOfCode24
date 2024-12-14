package src.day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day5 {
    static Map<Integer, Set<Integer>> pageOrderingRules = new HashMap<>();
    static List<List<Integer>> updates = new ArrayList<>();
    static List<List<Integer>> incorrectUpdates = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        int sum = 0;
        readFile();
        sum += checkUpdates();
        System.out.println(sum);

        int sum2 = 0;
        sum2 += orderIncorrectUpdates();
        System.out.println(sum2);
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
            if (pageOrderingRules.containsKey(ruleValues.get(0))) {
                pageOrderingRules.get(ruleValues.get(0)).add(ruleValues.get(1));
            } else {
                pageOrderingRules.put(ruleValues.get(0), new HashSet<>(Set.of(ruleValues.get(1))));
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            updates.add(new ArrayList<>(Arrays.stream(line.split(","))
                    .map(Integer::parseInt)
                    .toList()));
        }
        scanner.close();
    }

    private static int orderIncorrectUpdates() {
        int sum = 0;
        for (List<Integer> incorrectUpdate : incorrectUpdates) {
            for (int i = 0; i < incorrectUpdate.size(); i++) {
                while (i > 0 && !checkValue(incorrectUpdate, i)) {
                    swapValues(i, incorrectUpdate);
                    i--;
                }
            }
            sum += incorrectUpdate.get(incorrectUpdate.size() / 2);
        }
        return sum;
    }

    private static void swapValues(int i, List<Integer> updateToChange) {
        Collections.swap(updateToChange, i, i - 1);
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
            } else {
                incorrectUpdates.add(update);
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

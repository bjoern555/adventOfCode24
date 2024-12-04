package src.day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ListManipulator {
    static List<List<Integer>> lists = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        calculateSafeReports(false);
        calculateSafeReports(true);
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/lists-day2.txt");
        Scanner scanner = new Scanner(new FileReader(file));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            List<Integer> list = Arrays.stream(line.split(" "))
                    .map(Integer::parseInt)
                    .toList();
            lists.add(new ArrayList<>(list));
        }
        scanner.close();
    }


    private static void calculateSafeReports(boolean withDamper) {
        int safeReports = 0;
        for (List<Integer> list : lists) {
            if (withDamper) {
                for (int i = 0; i < list.size(); i++) {
                    List<Integer> manipulatedList = manipulateList(list, i);
                    if (isSafe(manipulatedList)) {
                        safeReports++;
                        break;
                    }
                }
            } else if (isSafe(list)) {
                safeReports++;
            }
        }
        System.out.println("Safe reports: " + safeReports);
    }

    private static boolean isSafe(List<Integer> list) {
        boolean ascending = list.getFirst() < list.get(1);
        for (int i = 0; i < list.size() - 1; i++) {
            if (unsafeDifference(list, i) || wrongDirection(list, ascending, i)) {
                return false;
            }
        }
        return true;
    }

    private static boolean wrongDirection(List<Integer> list, boolean ascending, int i) {
        return ascending && list.get(i) > list.get(i + 1) || (!ascending && list.get(i) < list.get(i + 1));
    }

    private static boolean unsafeDifference(List<Integer> list, int i) {
        return 1 > Math.abs(list.get(i) - list.get(i + 1)) || Math.abs(list.get(i) - list.get(i + 1)) > 3;
    }

    private static List<Integer> manipulateList(List<Integer> list, int i) {
        List<Integer> newList = new ArrayList<>(list);
        newList.remove(i);
        return newList;
    }
}
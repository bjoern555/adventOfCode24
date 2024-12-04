package src.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Day1 {
    static List<Integer> leftList = new ArrayList<>();
    static List<Integer> rightList = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        sortLists();
        calculateTotalDistance();
        calculateSimilarityScore();
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/input-day1.txt");
        Scanner scanner = new Scanner(new FileReader(file));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] numbers = line.split("\\s{3}");
            leftList.add(Integer.parseInt(numbers[0].trim()));
            rightList.add(Integer.parseInt(numbers[1].trim()));
        }
        scanner.close();
    }

    private static void sortLists() {
        leftList.sort(null);
        rightList.sort(null);
    }

    private static void calculateTotalDistance() {
        int totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        System.out.println(totalDistance);
    }

    private static void calculateSimilarityScore() {
        int similarityScore = 0;
        for (Integer valueLeft : leftList) {
            int counter = 0;
            for (Integer valueRight : rightList) {
                if (Objects.equals(valueLeft, valueRight)) {
                    counter++;
                }
            }
            similarityScore += counter * valueLeft;
        }
        System.out.println(similarityScore);
    }
}

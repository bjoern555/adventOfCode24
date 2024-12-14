package src.day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4 {
    static char[][] grid;
    static String forwardSearchword = "XMAS";
    static String backwardsSearchword = "SAMX";
    static String forwardSearchword2 = "MAS";
    static String backwardsSearchword2 = "SAM";

    public static void main(String[] args) throws FileNotFoundException {
        readFile();

        int counter = 0;
        counter += countSearchwords(forwardSearchword);
        counter += countSearchwords(backwardsSearchword);
        System.out.println("Count: " + counter);

        int masCounter = 0;
        List<String> searchwords = new ArrayList<>();
        searchwords.add(forwardSearchword2);
        searchwords.add(backwardsSearchword2);
        masCounter += countSearchwords2(searchwords);
        System.out.println("Count: " + masCounter);
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/input-day4.txt");
        Scanner scanner = new Scanner(new FileReader(file));

        grid = new char[140][140];

        int rowCount = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char[] chars = line.toCharArray();
            System.arraycopy(chars, 0, grid[rowCount], 0, chars.length);
            rowCount++;
        }
        scanner.close();
    }

    private static int countSearchwords(String searchword) {
        int counter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == searchword.charAt(0)) {
                    if (isSpace(j, searchword.length()) && horizontal(searchword, i, j)) {
                        counter++;
                    }

                    if (isSpace(i, searchword.length()) && vertical(searchword, i, j)) {
                        counter++;
                    }

                    if (isSpace(j, searchword.length()) && isSpace(i, searchword.length()) && diagonalDown(searchword, i, j)) {
                        counter++;
                    }

                    if (isSpace(j, searchword.length()) && isTopSpace(i, searchword.length()) && diagonalUp(searchword, i, j)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    private static int countSearchwords2(List<String> searchwords) {
        int counter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                for (String searchword : searchwords) {
                    if (grid[i][j] == searchword.charAt(0) && diagonalMatchDown(searchword, j, i) && diagonalMatchUp(searchwords, i, j)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    private static boolean diagonalMatchUp(List<String> searchwords, int i, int j) {
        return diagonalUp(searchwords.getFirst(), i + 2, j) || diagonalUp(searchwords.getLast(), i + 2, j);
    }

    private static boolean diagonalMatchDown(String searchword, int j, int i) {
        return isSpace(j, searchword.length()) && isSpace(i, searchword.length()) && diagonalDown(searchword, i, j);
    }

    private static boolean diagonalUp(String searchword, int i, int j) {
        return grid[i][j] == searchword.charAt(0) && java.util.stream.IntStream.range(1, searchword.length()).allMatch(k -> grid[i - k][j + k] == searchword.charAt(k));
    }

    private static boolean diagonalDown(String searchword, int i, int j) {
        return grid[i][j] == searchword.charAt(0) && java.util.stream.IntStream.range(1, searchword.length()).allMatch(k -> grid[i + k][j + k] == searchword.charAt(k));
    }

    private static boolean vertical(String searchword, int i, int j) {
        return grid[i][j] == searchword.charAt(0) && java.util.stream.IntStream.range(1, searchword.length()).allMatch(k -> grid[i + k][j] == searchword.charAt(k));
    }

    private static boolean horizontal(String searchword, int i, int j) {
        return grid[i][j] == searchword.charAt(0) && java.util.stream.IntStream.range(1, searchword.length()).allMatch(k -> grid[i][j + k] == searchword.charAt(k));
    }

    private static boolean isTopSpace(int i, int length) {
        return i >= length - 1;
    }

    private static boolean isSpace(int i, int length) {
        return i <= grid.length - length;
    }
}

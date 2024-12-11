package src.day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Day4 {
    static char[][] grid;
    static String forwardSearchword = "XMAS";
    static String backwardsSearchword = "SAMX";

    public static void main(String[] args) throws FileNotFoundException {
        int counter = 0;
        readFile();
        counter += countSearchwords(forwardSearchword);
        counter += countSearchwords(backwardsSearchword);
        System.out.println("Count: " + counter);
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

                    if (isSpace(j, searchword.length()) && isTopSpace(i) && diagonalUp(searchword, i, j)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    private static boolean diagonalUp(String searchword, int i, int j) {
        return grid[i - 1][j + 1] == searchword.charAt(1) && grid[i - 2][j + 2] == searchword.charAt(2) && grid[i - 3][j + 3] == searchword.charAt(3);
    }

    private static boolean diagonalDown(String searchword, int i, int j) {
        return grid[i + 1][j + 1] == searchword.charAt(1) && grid[i + 2][j + 2] == searchword.charAt(2) && grid[i + 3][j + 3] == searchword.charAt(3);
    }

    private static boolean vertical(String searchword, int i, int j) {
        return grid[i + 1][j] == searchword.charAt(1) && grid[i + 2][j] == searchword.charAt(2) && grid[i + 3][j] == searchword.charAt(3);
    }

    private static boolean horizontal(String searchword, int i, int j) {
        return grid[i][j + 1] == searchword.charAt(1) && grid[i][j + 2] == searchword.charAt(2) && grid[i][j + 3] == searchword.charAt(3);
    }

    private static boolean isTopSpace(int i) {
        return i >= 3;
    }

    private static boolean isSpace(int j, int length) {
        return j <= grid.length - length;
    }
}

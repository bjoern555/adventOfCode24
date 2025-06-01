package src.day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Day12 {
    static char[][] map;

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        System.out.println(calculateFencingPrice2());
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/input-day12");
        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }

        int size = lines.size();
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            map[i] = lines.get(i).toCharArray();
        }
    }

    private static int calculateFencingPrice() {
        int fencePrice = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != ' ' && map[i][j] != ' ') {
                    char searchChar = map[i][j];
                    int area = searchArea(searchChar, i, j);
                    int fences = searchFencesRecursive(searchChar, i, j);
                    System.out.println("Area: " + area + " Fences: " + fences + " Char: " + searchChar);
                    fencePrice += area * fences;
                    for (int k = 0; k < map.length; k++) {
                        for (int l = 0; l < map[k].length; l++) {
                            if (map[k][l] == '-') {
                                map[k][l] = ' ';
                            }
                        }
                    }
                }
            }
        }
        return fencePrice;
    }

    private static int calculateFencingPrice2() {
        int fencePrice = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != ' ') {
                    char searchChar = map[i][j];
                    int area = searchArea(searchChar, i, j);
                    int sides = searchSidesRecursive(searchChar, i, j);
                    System.out.println("Area: " + area + " Sides: " + sides + " Char: " + searchChar);
                    fencePrice += area * sides;
                    for (int k = 0; k < map.length; k++) {
                        for (int l = 0; l < map[k].length; l++) {
                            if (map[k][l] == '-') {
                                map[k][l] = ' ';
                            }
                        }
                    }
                }
            }
        }
        return fencePrice;
    }

    private static int searchArea(char searchChar, int startX, int startY) {
        int count = 0;
        boolean[][] visited = new boolean[map.length][map[0].length];
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});

        while (!stack.isEmpty()) {
            int[] pos = stack.pop();
            int i = pos[0];
            int j = pos[1];

            if (i < 0 || i >= map.length || j < 0 || j >= map[i].length || map[i][j] != searchChar || visited[i][j]) {
                continue;
            }

            visited[i][j] = true;
            count++;

            stack.push(new int[]{i + 1, j});
            stack.push(new int[]{i - 1, j});
            stack.push(new int[]{i, j + 1});
            stack.push(new int[]{i, j - 1});
        }

        return count;
    }

    private static int searchFencesRecursive(char searchChar, int i, int j) {
        map[i][j] = '-';
        int fences = 0;

        if (j + 1 >= map[i].length) {
            fences++;
        } else if (map[i][j + 1] == searchChar) {
            fences += searchFencesRecursive(searchChar, i, j + 1);
        } else if (map[i][j + 1] != '-') {
            fences++;
        }

        if (i + 1 >= map.length) {
            fences++;
        } else if (map[i + 1][j] == searchChar) {
            fences += searchFencesRecursive(searchChar, i + 1, j);
        } else if (map[i + 1][j] != '-') {
            fences++;
        }

        if (j - 1 < 0) {
            fences++;
        } else if (map[i][j - 1] == searchChar) {
            fences += searchFencesRecursive(searchChar, i, j - 1);
        } else if (map[i][j - 1] != '-') {
            fences++;
        }

        if (i - 1 < 0) {
            fences++;
        } else if (map[i - 1][j] == searchChar) {
            fences += searchFencesRecursive(searchChar, i - 1, j);
        } else if (map[i - 1][j] != '-') {
            fences++;
        }

        return fences;
    }

    private static int searchSidesRecursive(char searchChar, int i, int j) {
        map[i][j] = '-';
        int corners = 0;

        corners += countCorners(searchChar, i, j);

        if (j + 1 < map[i].length && map[i][j + 1] == searchChar) {
            corners += searchSidesRecursive(searchChar, i, j + 1);
        }
        if (i + 1 < map.length && map[i + 1][j] == searchChar) {
            corners += searchSidesRecursive(searchChar, i + 1, j);
        }
        if (j - 1 >= 0 && map[i][j - 1] == searchChar) {
            corners += searchSidesRecursive(searchChar, i, j - 1);
        }
        if (i - 1 >= 0 && map[i - 1][j] == searchChar) {
            corners += searchSidesRecursive(searchChar, i - 1, j);
        }

        return corners;
    }

    private static int countCorners(char searchChar, int i, int j) {
        int corners = 0;

        if ((j - 1 < 0 && i - 1 < 0) || (j - 1 < 0 && (map[i - 1][j] != searchChar && map[i - 1][j] != '-')) || (i - 1 < 0 && (map[i][j - 1] != searchChar && map[i][j - 1] != '-')) || (i - 1 >= 0 && j - 1 >= 0 && (map[i - 1][j] != searchChar && map[i - 1][j] != '-') && (map[i][j - 1] != searchChar && map[i][j - 1] != '-'))) {
            System.out.println("tl Corner: " + i + " " + j);
            corners++;
        }

        if (j - 1 >= 0 && i - 1 >= 0 && (map[i][j - 1] == searchChar || map[i][j - 1] == '-') && (map[i - 1][j] == searchChar || map[i - 1][j] == '-') && (map[i - 1][j - 1] != searchChar && map[i - 1][j - 1] != '-')) {
            corners++;
        }

        if ((j + 1 >= map[i].length && i - 1 < 0) || (j + 1 >= map[i].length && i - 1 >= 0 && (map[i - 1][j] != searchChar && map[i - 1][j] != '-')) || (i - 1 < 0 && j + 1 < map[i].length && (map[i][j + 1] != searchChar && map[i][j + 1] != '-')) || (i - 1 >= 0 && j + 1 < map[i].length && (map[i - 1][j] != searchChar && map[i - 1][j] != '-') && (map[i][j + 1] != searchChar && map[i][j + 1] != '-'))) {
            System.out.println("tr Corner: " + i + " " + j);
            corners++;
        }

        if (j + 1 < map[i].length && i - 1 >= 0 && (map[i - 1][j] == searchChar || map[i - 1][j] == '-') && (map[i][j + 1] == searchChar || map[i][j + 1] == '-') && (map[i - 1][j + 1] != searchChar && map[i - 1][j + 1] != '-')) {
            corners++;
        }

        if ((j - 1 < 0 && i + 1 >= map.length) || (j - 1 < 0 && i + 1 < map.length && (map[i + 1][j] != searchChar && map[i + 1][j] != '-')) || (i + 1 >= map.length && j - 1 >= 0 && (map[i][j - 1] != searchChar && map[i][j - 1] != '-')) || (i + 1 < map.length && j - 1 >= 0 && (map[i + 1][j] != searchChar && map[i + 1][j] != '-') && (map[i][j - 1] != searchChar && map[i][j - 1] != '-'))) {
            System.out.println("bl Corner: " + i + " " + j);
            corners++;
        }

        if (j - 1 >= 0 && i + 1 < map.length && (map[i + 1][j] == searchChar || map[i + 1][j] == '-') && (map[i][j - 1] == searchChar || map[i][j - 1] == '-') && (map[i + 1][j - 1] != searchChar && map[i + 1][j - 1] != '-')) {
            corners++;
        }

        if ((j + 1 >= map[i].length && i + 1 >= map.length) || (j + 1 >= map[i].length && i + 1 < map.length && (map[i + 1][j] != searchChar && map[i + 1][j] != '-')) || (i + 1 >= map.length && j + 1 < map[i].length && (map[i][j + 1] != searchChar && map[i][j + 1] != '-')) || (i + 1 < map.length && j + 1 < map[i].length && (map[i + 1][j] != searchChar && map[i + 1][j] != '-') && (map[i][j + 1] != searchChar && map[i][j + 1] != '-'))) {
            System.out.println("br Corner: " + i + " " + j);
            corners++;
        }

        if (j + 1 < map[i].length && i + 1 < map.length && (map[i + 1][j] == searchChar || map[i + 1][j] == '-') && (map[i][j + 1] == searchChar || map[i][j + 1] == '-') && (map[i + 1][j + 1] != searchChar && map[i + 1][j + 1] != '-')) {
            corners++;
        }

        return corners;
    }
}
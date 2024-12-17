package src.day8;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day8 {
    static char[][] map;
    static Set<Point> antinodes = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        evaluateAntinodes();
        for (Point antinode : antinodes) {
            map[antinode.x][antinode.y] = '@';
        }
        for (char[] row : map) {
            System.out.println(new String(row));
        }
        System.out.println("\n");
        System.out.println(antinodes.size());
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/input-day8");
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

    private static void evaluateAntinodes() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] != '.') {
                    for (int k = 0; k < map.length; k++) {
                        for (int l = 0; l < map.length; l++) {
                            if (map[i][j] == map[k][l] && !(i == k && j == l)) {
                                new Point(i, j);
                                createAntinode(new Point(i, j), new Point(k, l));
                            }
                        }
                    }
                }
            }
        }
    }

    private static void createAntinode(Point from, Point to) {
        int rowDifference = to.x - from.x;
        int colDifference = to.y - from.y;
        if (to.x + rowDifference >= 0 && to.x + rowDifference < map.length && to.y + colDifference >= 0 && to.y + colDifference < map.length) {
            antinodes.add(new Point(to.x + rowDifference, to.y + colDifference));
        }
    }
}

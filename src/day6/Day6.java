package src.day6;

import javax.swing.text.Position;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day6 {
    static char[][] map;

    public static void main(String[] args) throws FileNotFoundException {
        int sum = 0;
        readFile();
        simulateGuard();
        for (char[] row : map) {
            System.out.println(row);
        }
        sum += countGuardPath();
        System.out.println(sum);
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/input-day6");
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

    private static void simulateGuard() {
        Point startPosition = getStartPosition();
        Direction direction = Direction.UP;
        Point newPosition;
        while ((newPosition = move(startPosition, direction)) != null) {
            startPosition = newPosition;
            direction = direction.getNext();
        }
    }

    private static Point move(Point currentPosition, Direction direction) {
        Point tempPosition = new Point(currentPosition.x, currentPosition.y);
        Point lastPosition = new Point(currentPosition.x, currentPosition.y);

        while (theresSpace(tempPosition) && map[tempPosition.y][tempPosition.x] != '#') { //TODO why map[tempPosition.y][tempPosition.x] x und y ??
            map[tempPosition.x][tempPosition.y] = 'X';
            lastPosition.x = tempPosition.x;
            lastPosition.y = tempPosition.y;
            tempPosition.x += direction.getDx();
            tempPosition.y += direction.getDy();
        }
        if (map[tempPosition.y][tempPosition.x] != '#') {
            return lastPosition;
        } else {
            return null;
        }
    }

    private static boolean theresSpace(Point currentPosition) {
        return currentPosition.x > 0 && currentPosition.x < map.length - 1 && currentPosition.y > 0 && currentPosition.y < map.length - 1;
    }

    private static Point getStartPosition() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == '^') {
                    return new Point(i, j);
                }
            }
        }
        return new Point(0, 0);
    }

    private static int countGuardPath() {
        return 0;
    }

    private enum Direction {
        UP(-1, 0), RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1);

        private final int dx;
        private final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int getDx() {
            return dx;
        }

        public int getDy() {
            return dy;
        }

        public Direction getNext() {
            return switch (this) {
                case UP -> RIGHT;
                case RIGHT -> DOWN;
                case DOWN -> LEFT;
                case LEFT -> UP;
                default -> throw new IllegalStateException("Unexpected value: " + this);
            };
        }
    }
}
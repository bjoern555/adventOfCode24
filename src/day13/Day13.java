package src.day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Day13 {
    static List<List<LongPoint>> configurations = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        AtomicLong totalTokens = new AtomicLong(0);
        AtomicInteger currentIndex = new AtomicInteger(1); // Start from configuration 1

        System.out.println("Number of configurations: " + configurations.size());

        // Limit the number of threads to 15
        ForkJoinPool customThreadPool = new ForkJoinPool(15); // Use 15 threads
        try {
            customThreadPool.submit(() ->
                    configurations.parallelStream().forEach(configuration -> {
                        int index = currentIndex.getAndIncrement(); // Get and increment the index
                        System.out.println("Processing configuration: " + index);

                        long tokens = evaluateTokens(configuration);
                        totalTokens.addAndGet(tokens);
                    })
            ).get(); // Wait for completion
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            customThreadPool.shutdown(); // Clean up the custom thread pool
        }

        System.out.println("Total tokens: " + totalTokens.get());
    }

    private static void readFile() throws FileNotFoundException {
        File file = new File("resources/input-day13");
        Scanner scanner = new Scanner(new FileReader(file));
        List<LongPoint> currentConfig = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Button A:")) {
                currentConfig.add(parsePoint(line));
            } else if (line.startsWith("Button B:")) {
                currentConfig.add(parsePoint(line));
            } else if (line.startsWith("Prize:")) {
                currentConfig.add(parsePoint(line));
                configurations.add(new ArrayList<>(currentConfig));
                currentConfig.clear();
            }
        }
        scanner.close();
    }

    private static LongPoint parsePoint(String line) {
        String[] parts = line.split("[, ]+");
        long x = 0;
        long y = 0;

        if (parts.length >= 3) {
            if (parts[2].startsWith("X+")) {
                x = Integer.parseInt(parts[2].replace("X+", ""));
                y = Integer.parseInt(parts[3].replace("Y+", ""));
            } else if (parts[1].startsWith("X=")) {
                x = Integer.parseInt(parts[1].replace("X=", "")) * 10000000000000L;
                y = Integer.parseInt(parts[2].replace("Y=", "")) * 10000000000000L;
            }
        }
        return new LongPoint(x, y);
    }

    private static long evaluateTokens(List<LongPoint> configuration) {
        LongPoint a = configuration.get(0);
        LongPoint b = configuration.get(1);
        LongPoint prize = configuration.get(2);

        long maxJ = evaluateBiggest(b, prize);

        for (long j = maxJ; j >= 0; j--) {
            long remainingX = prize.x - j * b.x;
            long remainingY = prize.y - j * b.y;

            if (remainingX % a.x == 0 && remainingY % a.y == 0) {
                return j + 3 * remainingX / a.x;
            }
        }

        return 0;
    }

    private static long evaluateBiggest(LongPoint vector, LongPoint prize) {
        long x = prize.x / vector.x;
        long y = prize.y / vector.y;

        return Math.min(x, y);
    }
}
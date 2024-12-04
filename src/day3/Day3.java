package src.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    static String mulRegex = "mul\\((\\d+),(\\d+)\\)";
    static List<Integer> products = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        readFileAndComputeProducts();
        calculateSum();
    }

    private static void readFileAndComputeProducts() throws FileNotFoundException {
        File file = new File("resources/input-day3.txt");
        Scanner scanner = new Scanner(new FileReader(file));
        Pattern pattern = Pattern.compile(mulRegex);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                products.add(x * y);
            }
        }
        scanner.close();
    }

    private static void calculateSum() {
        int sum = products.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum: " + sum);
    }
}

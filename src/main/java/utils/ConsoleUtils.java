package utils;

import java.util.Scanner;

public class ConsoleUtils {

    private final Scanner scanner;

    public ConsoleUtils() {
        this.scanner = new Scanner(System.in);
    }

    public String getNextLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public String getNextLine(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        return input.isEmpty() ? defaultValue : input;
    }

    public void print(String message) {
        System.out.println(message);
    }
}

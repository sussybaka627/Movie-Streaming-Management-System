package utils;

import java.util.Scanner;

public class ValidationUtil {
    public static String getString(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Input cannot be empty. Please try again.");
        }
    }
    public static int getInt(Scanner scanner, String prompt, int min, int max) {
        int result;
        while (true) {
            System.out.print(prompt);
            try {
                result = Integer.parseInt(scanner.nextLine().trim());
                if (result >= min && result <= max) {
                    return result;
                }
                System.out.printf("Error: Please enter a number between %d and %d.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format. Please enter an integer.");
            }
        }
    }

    public static double getDouble(Scanner scanner, String prompt, double min, double max) {
        double result;
        while (true) {
            System.out.print(prompt);
            try {
                result = Double.parseDouble(scanner.nextLine().trim());
                if (result >= min && result <= max) {
                    return result;
                }
                System.out.printf("Error: Please enter a number between %.1f and %.1f.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format. Please enter a valid decimal.");
            }
        }
    }
    public static boolean getConfirm(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            }
            System.out.println("Error: Please enter 'Y' for Yes or 'N' for No.");
        }
    }

    public static String getStringForUpdate(Scanner scanner, String prompt, String oldValue) {
        while (true) {
            System.out.print(prompt + " (Enter '*' to keep '" + oldValue + "'): ");
            String input = scanner.nextLine().trim();
            if (input.equals("*")) return oldValue;
            if (!input.isEmpty()) return input;
            System.out.println("Error: Input cannot be empty. Please try again.");
        }
    }

    public static int getIntForUpdate(Scanner scanner, String prompt, int oldValue, int min, int max) {
        while (true) {
            System.out.print(prompt + " (Enter '*' to keep '" + oldValue + "'): ");
            String input = scanner.nextLine().trim();
            if (input.equals("*")) return oldValue;
            try {
                int result = Integer.parseInt(input);
                if (result >= min && result <= max) return result;
                System.out.printf("Error: Please enter a number between %d and %d.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid format. Please enter an integer or '*'.");
            }
        }
    }

    public static double getDoubleForUpdate(Scanner scanner, String prompt, double oldValue, double min, double max) {
        while (true) {
            System.out.print(prompt + " (Enter '*' to keep '" + oldValue + "'): ");
            String input = scanner.nextLine().trim();
            if (input.equals("*")) return oldValue;
            try {
                double result = Double.parseDouble(input);
                if (result >= min && result <= max) return result;
                System.out.printf("Error: Please enter a number between %.1f and %.1f.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid format. Please enter a valid decimal or '*'.");
            }
        }
    }
}
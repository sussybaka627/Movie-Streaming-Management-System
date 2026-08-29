package views;

import java.util.Scanner;

public class MainView {
    private Scanner scanner;
    
    public MainView() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            displayMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    manageCategoriesMenu();
                    break;
                case 2:
                    manageMoviesMenu();
                    break;
                case 3:
                    searchAndSortMenu();
                    break;
                case 4:
                    watchlistMenu();
                    break;
                case 5:
                    historyAndStatsMenu();
                    break;
                case 0:
                    System.out.println("Saving data and exiting.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 0 and 5.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n=============================================");
        System.out.println("    NETFLIX-LIKE MOVIE STREAMING SYSTEM      ");
        System.out.println("=============================================");
        System.out.println("1. Manage Categories (Category CRUD)");
        System.out.println("2. Manage Movies (Movie CRUD)");
        System.out.println("3. Search & Sort Movies");
        System.out.println("4. Watchlist & Favorites");
        System.out.println("5. Viewing History & Statistics");
        System.out.println("0. Save & Exit");
        System.out.println("=============================================");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1; 
        }
    }

    private void manageCategoriesMenu() {
        System.out.println("\n--- CATEGORY MANAGEMENT ---");
        System.out.println("1. View all categories");
        System.out.println("2. Add a new category");
        System.out.println("3. Update a category");
        System.out.println("4. Delete a category");
        System.out.println("0. Go back");
        System.out.print("Choice: ");
        scanner.nextLine(); 
    }

    private void manageMoviesMenu() {
        System.out.println("\n--- MOVIE MANAGEMENT ---");
        System.out.println("(Under construction... Press Enter to go back)");
        scanner.nextLine();
    }

    private void searchAndSortMenu() {
        System.out.println("\n--- SEARCH & SORT ---");
        System.out.println("(Under construction... Press Enter to go back)");
        scanner.nextLine();
    }

    private void watchlistMenu() {
        System.out.println("\n--- WATCHLIST & FAVORITES ---");
        System.out.println("(Under construction... Press Enter to go back)");
        scanner.nextLine();
    }

    private void historyAndStatsMenu() {
        System.out.println("\n--- HISTORY & STATISTICS ---");
        System.out.println("(Under construction... Press Enter to go back)");
        scanner.nextLine();
    }
}
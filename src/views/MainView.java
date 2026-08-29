package views;
import controllers.CategoryController;
import models.entities.Category;
import utils.ValidationUtil;
import java.util.List;
import java.util.Scanner;

public class MainView {
    private Scanner scanner;
    private CategoryController categoryController = new CategoryController();
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
    boolean back = false;
    while (!back) {
        System.out.println("\n--- CATEGORY MANAGEMENT ---");
        System.out.println("1. View all categories");
        System.out.println("2. Add a new category");
        System.out.println("3. Update a category");
        System.out.println("4. Delete a category");
        System.out.println("0. Go back");
        
        int choice = ValidationUtil.getInt(scanner, "Choice: ", 0, 4);

        switch (choice) {
            case 1:
                List<Category> list = categoryController.getAllCategories();
                if (list.isEmpty()) {
                    System.out.println("No categories found.");
                } else {
                    for (Category c : list) {
                        System.out.println(c.toString());
                    }
                }
                break;
            case 2:
                String id = ValidationUtil.getString(scanner, "Enter Category ID (e.g., C01): ");
                String name = ValidationUtil.getString(scanner, "Enter Category Name: ");
                if (categoryController.addCategory(id, name)) {
                    System.out.println("Category added successfully!");
                } else {
                    System.out.println("Error: Category ID already exists.");
                }
                break;
            case 3:
                String updateId = ValidationUtil.getString(scanner, "Enter Category ID to update: ");
                String newName = ValidationUtil.getString(scanner, "Enter new Category Name: ");
                if (categoryController.updateCategory(updateId, newName)) {
                    System.out.println("Category updated successfully!");
                } else {
                    System.out.println("Error: Category not found.");
                }
                break;
            case 4:
                String deleteId = ValidationUtil.getString(scanner, "Enter Category ID to delete: ");
                if (categoryController.deleteCategory(deleteId)) {
                    System.out.println("Category deleted successfully!");
                } else {
                    System.out.println("Error: Category not found.");
                }
                break;
            case 0:
                back = true;
                break;
            }
        }
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
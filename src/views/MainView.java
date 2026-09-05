package views;

import controllers.AuthController;
import controllers.CategoryController;
import controllers.HistoryController;
import controllers.MovieController;
import controllers.WatchlistController;
import models.datastructures.MyLinkedList;
import models.entities.Account;
import models.entities.Category;
import models.entities.Movie;
import models.entities.WatchRecord;
import utils.ValidationUtil;
import data.FileHandler;

import java.util.Scanner;

public class MainView {
    private Scanner scanner;
    private FileHandler fileHandler;
    private AuthController authController;
    private CategoryController categoryController = new CategoryController();
    private MovieController movieController = new MovieController();
    private WatchlistController watchlistController = new WatchlistController();
    private HistoryController historyController = new HistoryController();

    public MainView() {
        this.scanner = new Scanner(System.in);
        this.fileHandler = new FileHandler();
        this.authController = new AuthController(fileHandler);
    }

    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n=============================================");
            System.out.println("          NETFLIX CLONE - AUTHENTICATION     ");
            System.out.println("=============================================");
            System.out.println("1. Login");
            System.out.println("2. Register New User Account");
            System.out.println("0. Save & Exit System");
            System.out.println("=============================================");

            int choice = ValidationUtil.getInt(scanner, "Choice: ", 0, 2);

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegister();
                    break;
                case 0:
                    System.out.println("System shutting down. Goodbye!");
                    isRunning = false;
                    break;
            }
        }
    }

    private void handleLogin() {
        System.out.println("\n--- LOGIN ---");
        String user = ValidationUtil.getString(scanner, "Username: ");
        String pass = ValidationUtil.getString(scanner, "Password: ");

        Account acc = authController.login(user, pass);
        if (acc == null) {
            System.out.println("Error: Invalid username or password!");
        } else {
            System.out.println("Login successful! Welcome, " + acc.getName());
            if (acc.getRole().equals("ADMIN")) {
                adminSession();
            } else {
                userSession();
            }
        }
    }

    private void handleRegister() {
        System.out.println("\n--- REGISTER NEW USER ---");
        String user = ValidationUtil.getString(scanner, "Username: ");
        
        if (authController.isUsernameTaken(user)) {
            System.out.println("Error: Username is already taken!");
            return;
        }

        String name = ValidationUtil.getString(scanner, "Full Name: ");
        String phone = ValidationUtil.getString(scanner, "Phone Number: ");
        String email = ValidationUtil.getString(scanner, "Email Address: ");
        String pass = ValidationUtil.getString(scanner, "Password: ");

        if (authController.registerUser(user, name, phone, email, pass)) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed.");
        }
    }

    private void adminSession() {
        boolean adminActive = true;
        while (adminActive) {
            System.out.println("\n--- ADMIN CONTROL PANEL ---");
            System.out.println("1. Manage Categories (CRUD)");
            System.out.println("2. Manage Movies (CRUD)");
            System.out.println("3. Generate Viewing Reports");
            System.out.println("0. Logout");
            
            int choice = ValidationUtil.getInt(scanner, "Admin Choice: ", 0, 3);
            
            switch (choice) {
                case 1: manageCategoriesMenu(); break;
                case 2: manageMoviesMenu(); break;
                case 3: generateViewingReport(); break;
                case 0: 
                    System.out.println("Logging out of Admin Panel...");
                    adminActive = false; 
                    break;
            }
        }
    }

    private void userSession() {
        boolean userActive = true;
        while (userActive) {
            System.out.println("\n--- USER DASHBOARD ---");
            System.out.println("1. Browse, Search & Sort Movies");
            System.out.println("2. My Watchlist");
            System.out.println("3. My Favorite Movies");
            System.out.println("4. My Viewing History & Stats");
            System.out.println("5. View Top 10 Best Movie Ranking");
            System.out.println("0. Logout");
            
            int choice = ValidationUtil.getInt(scanner, "User Choice: ", 0, 5);
            
            switch (choice) {
                case 1: searchAndSortMenu(); break;
                case 2: watchlistMenu(); break;
                case 3: favoriteMoviesMenu(); break;
                case 4: historyAndStatsMenu(); break;
                case 5: displayTop10Ranking(); break;
                case 0: 
                    System.out.println("Logging out of User Dashboard...");
                    userActive = false; 
                    break;
            }
        }
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
                    MyLinkedList<Category> list = categoryController.getAllCategories();
                    if (list.isEmpty()) {
                        System.out.println("No categories found.");
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i).toString());
                        }
                    }
                    break;
                case 2:
                    String id = ValidationUtil.getString(scanner, "Enter Category ID (e.g., C01): ");
                    if (categoryController.findCategoryById(id) != null) {
                    System.out.println("Error: Category ID already exists.");
                    break;
                    }
                    String name = ValidationUtil.getString(scanner, "Enter Category Name: ");
                    if (categoryController.isNameTaken(name)) {
                        System.out.println("Error: Category Name '" + name + "' already exists.");
                        break;
                    }
                    if (categoryController.addCategory(id, name)) {
                        System.out.println("Category added successfully!");
                    } else {
                        System.out.println("Error: Failed to add category.");
                    }
                    break;
                case 3:
                    String updateId = ValidationUtil.getString(scanner, "Enter Category ID to update: ");
                    Category currentCat = categoryController.findCategoryById(updateId);
                    
                    if (currentCat == null) {
                        System.out.println("Error: Category not found.");
                        break;
                    }
                    String newName = ValidationUtil.getString(scanner, "Enter new Category Name: ");
                    if (!currentCat.getName().equalsIgnoreCase(newName.trim()) && categoryController.isNameTaken(newName)) {
                        System.out.println("Error: Category Name '" + newName + "' already exists.");
                        break;
                    }
                    if (categoryController.updateCategory(updateId, newName)) {
                        System.out.println("Category updated successfully!");
                    }
                    break;
                case 4:
                    String deleteId = ValidationUtil.getString(scanner, "Enter Category ID to delete: ");
                    if (categoryController.findCategoryById(deleteId) == null) {
                        System.out.println("Error: Category not found.");
                        break;
                    }
                    MyLinkedList<Movie> linkedMovies = movieController.getMoviesByCategory(deleteId);
                    if (!linkedMovies.isEmpty()) {
                        System.out.println("Error: Cannot delete this category. " + linkedMovies.size() + " movie(s) are using it.");
                        break;
                    }
                    if (categoryController.deleteCategory(deleteId)) {
                        System.out.println("Category deleted successfully!");
                    }
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void manageMoviesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- MOVIE MANAGEMENT ---");
            System.out.println("1. View all movies");
            System.out.println("2. Add a new movie");
            System.out.println("3. Update a movie");
            System.out.println("4. Delete a movie");
            System.out.println("5. View movie details");
            System.out.println("0. Go back");
            
            int choice = ValidationUtil.getInt(scanner, "Choice: ", 0, 5);

            switch (choice) {
                case 1:
                    MyLinkedList<Movie> list = movieController.getAllMovies();
                    if (list.isEmpty()) {
                        System.out.println("No movies found.");
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i).toString());
                        }
                    }
                    break;

                case 2:
                    System.out.println("\n[ Add New Movie ]");
                    String id = ValidationUtil.getString(scanner, "Enter Movie ID (e.g., M01): ");
                    
                    if (movieController.findMovieById(id) != null) {
                        System.out.println("Error: Movie ID already exists.");
                        break;
                    }

                    String title = ValidationUtil.getString(scanner, "Enter Title: ");
                    String director = ValidationUtil.getString(scanner, "Enter Director: ");
                    String actor = ValidationUtil.getString(scanner, "Enter Main Actor: ");

                    System.out.println("\n--- Available Categories ---");
                    MyLinkedList<Category> cats = categoryController.getAllCategories();
                    if (cats.isEmpty()) {
                        System.out.println("(No categories available. You should add categories first.)");
                    } else {
                        for (int i = 0; i < cats.size(); i++) {
                            Category c = cats.get(i);
                            System.out.println("- " + c.getId() + ": " + c.getName());
                        }
                    }
                    
                    String categoryId = ValidationUtil.getString(scanner, "Enter Category ID: ");
                    double rating = ValidationUtil.getDouble(scanner, "Enter Rating (0.0 - 10.0): ", 0.0, 10.0);
                    int year = ValidationUtil.getInt(scanner, "Enter Release Year (1900 - 2026): ", 1900, 2026);
                    int duration = ValidationUtil.getInt(scanner, "Enter Duration in minutes (1 - 500): ", 1, 500);

                    if (movieController.addMovie(id, title, director, actor, categoryId, rating, year, duration)) {
                        System.out.println("Movie added successfully!");
                    } else {
                        System.out.println("Failed to add movie.");
                    }
                    break;

                case 3:
                    System.out.println("\n[ Update Movie ]");
                    String updateId = ValidationUtil.getString(scanner, "Enter Movie ID to update: ");
                    Movie existingMovie = movieController.findMovieById(updateId);
                    
                    if (existingMovie == null) {
                        System.out.println("Error: Movie not found.");
                        break;
                    }
                    
                    System.out.println("Updating Movie: " + existingMovie.getTitle());
                    String newTitle = ValidationUtil.getString(scanner, "Enter new Title: ");
                    String newDirector = ValidationUtil.getString(scanner, "Enter new Director: ");
                    String newActor = ValidationUtil.getString(scanner, "Enter new Main Actor: ");
                    String newCatId = ValidationUtil.getString(scanner, "Enter new Category ID: ");
                    double newRating = ValidationUtil.getDouble(scanner, "Enter new Rating (0.0 - 10.0): ", 0.0, 10.0);
                    int newYear = ValidationUtil.getInt(scanner, "Enter new Release Year (1900 - 2026): ", 1900, 2026);
                    int newDuration = ValidationUtil.getInt(scanner, "Enter new Duration in minutes (1 - 500): ", 1, 500);

                    if (movieController.updateMovie(updateId, newTitle, newDirector, newActor, newCatId, newRating, newYear, newDuration)) {
                        System.out.println("Movie updated successfully!");
                    }
                    break;

                case 4:
                    System.out.println("\n[ Delete Movie ]");
                    String deleteId = ValidationUtil.getString(scanner, "Enter Movie ID to delete: ");
                    
                    if (movieController.findMovieById(deleteId) == null) {
                        System.out.println("Error: Movie not found.");
                        break;
                    }

                    boolean confirm = ValidationUtil.getConfirm(scanner, "Are you sure you want to delete this movie?");
                    if (confirm) {
                        if (movieController.deleteMovie(deleteId)) {
                            System.out.println("Movie deleted successfully!");
                        }
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                    break;
                case 5:
                    System.out.println("\n[ View Movie Details ]");
                    String detailId = ValidationUtil.getString(scanner, "Enter Movie ID: ");
                    Movie detailMovie = movieController.findMovieById(detailId);
                    
                    if (detailMovie == null) {
                        System.out.println("Error: Movie not found.");
                    } else {
                        System.out.println("\n=============================================");
                        System.out.println("               MOVIE DETAILS                 ");
                        System.out.println("=============================================");
                        System.out.println("ID           : " + detailMovie.getId());
                        System.out.println("Title        : " + detailMovie.getTitle());
                        System.out.println("Director     : " + detailMovie.getDirector());
                        System.out.println("Main Actor   : " + detailMovie.getActor());

                        Category cat = categoryController.findCategoryById(detailMovie.getCategoryId());
                        String catName = (cat != null) ? cat.getName() : "Unknown";

                        System.out.println("Category     : " + catName + " (" + detailMovie.getCategoryId() + ")");
                        System.out.println("Release Year : " + detailMovie.getReleaseYear());
                        System.out.println("Duration     : " + detailMovie.getDurationMinutes() + " mins");
                        System.out.println("Rating       : " + detailMovie.getRating() + "/10.0");
                        System.out.println("Views        : " + detailMovie.getViews());
                        System.out.println("Favorites    : " + detailMovie.getFavorites());
                        System.out.printf("Ranking Score: %.2f\n", detailMovie.getRankingScore());
                        System.out.println("=============================================");
                    }
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void searchAndSortMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- SEARCH & SORT ---");
            System.out.println("1. Search movies (by Title/Actor/Director)");
            System.out.println("2. Sort movies");
            System.out.println("3. Browse movies by Category");
            System.out.println("0. Go back");
            
            int choice = ValidationUtil.getInt(scanner, "Choice: ", 0, 3);

            switch (choice) {
                case 1:
                    String keyword = ValidationUtil.getString(scanner, "Enter keyword to search: ");
                    MyLinkedList<Movie> results = movieController.searchMovies(keyword);
                    
                    if (results.isEmpty()) {
                        System.out.println("No movies found matching: '" + keyword + "'");
                    } else {
                        System.out.println("\n--- Search Results ---");
                        for (int i = 0; i < results.size(); i++) {
                            System.out.println(results.get(i).toString());
                        }
                        System.out.println("----------------------");
                        System.out.println("Total: " + results.size() + " movie(s) found.");
                    }
                    break;
                case 2:
                    System.out.println("\n--- SORT MOVIES ---");
                    System.out.println("1. By Title (A to Z)");
                    System.out.println("2. By Rating (High to Low)");
                    System.out.println("3. By Release Year (Newest to Oldest)");
                    System.out.println("4. By Popularity (Most Views)");
                    System.out.println("0. Cancel");
                    
                    int sortChoice = ValidationUtil.getInt(scanner, "Choose sort criteria: ", 0, 4);
                    
                    switch (sortChoice) {
                        case 1:
                            movieController.sortMoviesByTitle();
                            System.out.println("\nMovies sorted by Title successfully!");
                            break;
                        case 2:
                            movieController.sortMoviesByRating();
                            System.out.println("\nMovies sorted by Rating successfully!");
                            break;
                        case 3:
                            movieController.sortMoviesByYear();
                            System.out.println("\nMovies sorted by Release Year successfully!");
                            break;
                        case 4:
                            movieController.sortMoviesByPopularity();
                            System.out.println("\nMovies sorted by Popularity successfully!");
                            break;
                    }
                    
                    if (sortChoice != 0) {
                        System.out.println("\n--- Sorted Movie List ---");
                        MyLinkedList<Movie> sortedList = movieController.getAllMovies();
                        for (int i = 0; i < sortedList.size(); i++) {
                            System.out.println(sortedList.get(i).toString());
                        }
                        System.out.println("-------------------------");
                    }
                    break;
                case 3:
                    System.out.println("\n--- Available Categories ---");
                    MyLinkedList<Category> cats = categoryController.getAllCategories();
                    if (cats.isEmpty()) {
                        System.out.println("(No categories available.)");
                    } else {
                        for (int i = 0; i < cats.size(); i++) {
                            Category c = cats.get(i);
                            System.out.println("- " + c.getId() + ": " + c.getName());
                        }
                        String catInput = ValidationUtil.getString(scanner, "\nEnter Category ID or Name to browse: ").toLowerCase();
                        
                        String targetCatId = catInput;
                        String targetCatName = catInput;
                        for (int i = 0; i < cats.size(); i++) {
                            Category c = cats.get(i);
                            if (c.getId().toLowerCase().equals(catInput) || 
                                c.getName().toLowerCase().contains(catInput)) {
                                targetCatId = c.getId();
                                targetCatName = c.getName();
                                break;
                            }
                        }
                        
                        MyLinkedList<Movie> catMovies = movieController.getMoviesByCategory(targetCatId);
                        
                        if (catMovies.isEmpty()) {
                            System.out.println("No movies found for category: '" + catInput + "'");
                        } else {
                            System.out.println("\n--- Movies in Category: " + targetCatName + " (" + targetCatId + ") ---");
                            for (int i = 0; i < catMovies.size(); i++) {
                                System.out.println(catMovies.get(i).toString());
                            }
                            System.out.println("Total: " + catMovies.size() + " movie(s).");
                        }
                    }
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void watchlistMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- WATCHLIST (PLAYLIST) ---");
            System.out.println("1. View Watchlist");
            System.out.println("2. Add Movie to Watchlist");
            System.out.println("3. Watch Next Movie (Dequeue)");
            System.out.println("4. Undo Last Watch (Pop Stack)");
            System.out.println("0. Go back");
            
            int choice = ValidationUtil.getInt(scanner, "Choice: ", 0, 4);

            switch (choice) {
                case 1:
                    watchlistController.displayWatchlist();
                    break;
                case 2:
                    String movieId = ValidationUtil.getString(scanner, "Enter Movie ID to add: ");
                    Movie movie = movieController.findMovieById(movieId);
                    
                    if (movie != null) {
                        watchlistController.addMovieToWatchlist(movie);
                        System.out.println("Added '" + movie.getTitle() + "' to your watchlist!");
                    } else {
                        System.out.println("Error: Movie ID not found.");
                    }
                    break;
                case 3:
                    Movie watchedMovie = watchlistController.watchNext();
                    if (watchedMovie != null) {
                        System.out.println("\n▶ Now watching: " + watchedMovie.getTitle());
                        System.out.println("Total duration: " + watchedMovie.getDurationMinutes() + " mins");

                        int minutesToWatch = ValidationUtil.getInt(scanner, 
                            "How many minutes will you watch now? (1 - " + watchedMovie.getDurationMinutes() + "): ", 
                            1, watchedMovie.getDurationMinutes());

                        if (historyController.getRecordByMovieId(watchedMovie.getId()) == null) {
                            watchedMovie.setViews(watchedMovie.getViews() + 1);
                            movieController.saveAllMoviesData();
                        }

                        historyController.saveOrUpdateRecord(watchedMovie.getId(), minutesToWatch);
                        System.out.println("Watched " + minutesToWatch + " minutes. Progress saved!");
                            
                    } else {
                        System.out.println("Your watchlist is empty! Add some movies first.");
                    }
                    break;
                case 4:
                    Movie restoredMovie = watchlistController.undoLastWatch();
                    if (restoredMovie != null) {
                        System.out.println("Undo successful! '" + restoredMovie.getTitle() + "' has been added back to the watchlist.");
                    } else {
                        System.out.println("Nothing to undo.");
                    }
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void favoriteMoviesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- FAVORITE MOVIES ---");
            System.out.println("1. View my favorite movies");
            System.out.println("2. Add a movie to favorites");
            System.out.println("3. Remove a movie from favorites");
            System.out.println("0. Go back");
            
            int choice = ValidationUtil.getInt(scanner, "Choice: ", 0, 3);

            switch (choice) {
                case 1:
                    MyLinkedList<Movie> favMovies = movieController.getFavoriteMovies();
                    if (favMovies.isEmpty()) {
                        System.out.println("You haven't favorited any movies yet.");
                    } else {
                        System.out.println("\n[ My Favorites List ]");
                        for (int i = 0; i < favMovies.size(); i++) {
                            System.out.println(favMovies.get(i).toString());
                        }
                    }
                    break;
                case 2:
                    String addId = ValidationUtil.getString(scanner, "Enter Movie ID to add to favorites: ");
                    if (movieController.toggleFavorite(addId, true)) {
                        System.out.println("Added to favorites successfully!");
                    } else {
                        System.out.println("Error: Movie ID not found.");
                    }
                    break;
                case 3:
                    String removeId = ValidationUtil.getString(scanner, "Enter Movie ID to remove from favorites: ");
                    if (movieController.toggleFavorite(removeId, false)) {
                        System.out.println("Removed from favorites successfully!");
                    } else {
                        System.out.println("Error: Movie ID not found.");
                    }
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void historyAndStatsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- HISTORY & STATISTICS ---");
            System.out.println("1. View Watching History (Full Log)");
            System.out.println("2. Recently Watched Movies (Top 3)");
            System.out.println("3. View My Statistics Dashboard");
            System.out.println("4. Trending Movies (Most Viewed)");
            System.out.println("5. Continue Watching");
            System.out.println("6. Trending Categories (Most Viewed)");
            System.out.println("0. Go back");

            int choice = ValidationUtil.getInt(scanner, "Choice: ", 0, 6); 

            switch (choice) {
                case 1:
                    MyLinkedList<WatchRecord> historyList = historyController.getAllHistory();
                    if (historyList.isEmpty()) {
                        System.out.println("You haven't watched any movies yet.");
                    } else {
                        System.out.println("\n--- Full Watching History ---");
                        for (int i = 0; i < historyList.size(); i++) {
                            WatchRecord record = historyList.get(i);
                            Movie m = movieController.findMovieById(record.getMovieId());
                            String title = (m != null) ? m.getTitle() : "Unknown Movie";
                            System.out.printf("%d. %s | Time: %d mins\n", (i+1), title, record.getWatchedMinutes());
                        }
                    }
                    break;
                    
                case 2:
                    MyLinkedList<WatchRecord> allHistoryForRecent = historyController.getAllHistory();
                    if (allHistoryForRecent.isEmpty()) {
                        System.out.println("You haven't watched any movies yet.");
                    } else {
                        System.out.println("\n--- Recently Watched (Quick Access) ---");

                        MyLinkedList<WatchRecord> recentList = new MyLinkedList<>();
                        for (int i = 0; i < allHistoryForRecent.size(); i++) {
                            recentList.add(allHistoryForRecent.get(i));
                        }

                        for (int i = 0; i < recentList.size() - 1; i++) {
                            for (int j = 0; j < recentList.size() - i - 1; j++) {
                                WatchRecord r1 = recentList.get(j);
                                WatchRecord r2 = recentList.get(j + 1);
                                if (r1.getLastWatchTime() < r2.getLastWatchTime()) {
                                    recentList.set(j, r2);
                                    recentList.set(j + 1, r1);
                                }
                            }
                        }

                        int limit = Math.min(3, recentList.size());
                        for (int i = 0; i < limit; i++) {
                            WatchRecord record = recentList.get(i);
                            Movie m = movieController.findMovieById(record.getMovieId());
                            String title = (m != null) ? m.getTitle() : "Unknown Movie";
                            System.out.printf("Recent %d: %s | Watched: %d mins\n", (i+1), title, record.getWatchedMinutes());
                        }
                    }
                    break;
                    
                case 3:
                    System.out.println("\n=============================================");
                    System.out.println("             USER DASHBOARD STATS            ");
                    System.out.println("=============================================");
                    
                    int totalMinutes = historyController.getTotalWatchTime();
                    int totalMoviesWatched = historyController.getAllHistory().size();
                    int favCount = movieController.getFavoriteMovies().size();
                    
                    System.out.println("Total Movies Watched : " + totalMoviesWatched);
                    System.out.println("Total Watch Time     : " + totalMinutes + " mins (" + (totalMinutes/60) + " hours)");
                    System.out.println("Movies in Favorites  : " + favCount);
                    System.out.println("=============================================");
                    break;
                    
                case 4:
                    System.out.println("\n--- Trending Movies (Top 3 Most Viewed) ---");
                    MyLinkedList<Movie> originalMovies = movieController.getAllMovies();
                    MyLinkedList<Movie> tempSortList = new MyLinkedList<>();
                    
                    for (int i = 0; i < originalMovies.size(); i++) {
                        tempSortList.add(originalMovies.get(i));
                    }
                    
                    for (int i = 0; i < tempSortList.size() - 1; i++) {
                        for (int j = 0; j < tempSortList.size() - i - 1; j++) {
                            Movie m1 = tempSortList.get(j);
                            Movie m2 = tempSortList.get(j + 1);
                            if (m1.getViews() < m2.getViews()) {
                                tempSortList.set(j, m2);
                                tempSortList.set(j + 1, m1);
                            }
                        }
                    }
                    
                    int movieLimit = Math.min(3, tempSortList.size());
                    boolean hasTrendingMovie = false;
                    for (int i = 0; i < movieLimit; i++) {
                        Movie m = tempSortList.get(i);
                        if (m.getViews() > 0) {
                            System.out.printf("TOP %d: %s | %d Views\n", (i+1), m.getTitle(), m.getViews());
                            hasTrendingMovie = true;
                        }
                    }
                    if (!hasTrendingMovie) {
                        System.out.println("No viewing data available yet to determine trends.");
                    }
                    break;
                    
                case 5:
                    System.out.println("\n--- Continue Watching ---");
                    MyLinkedList<WatchRecord> allHistory = historyController.getAllHistory();
                    boolean hasUnfinished = false;
                    
                    for (int i = 0; i < allHistory.size(); i++) {
                        WatchRecord record = allHistory.get(i);
                        Movie m = movieController.findMovieById(record.getMovieId());
                        
                        if (m != null && record.getWatchedMinutes() < m.getDurationMinutes()) {
                            System.out.printf("- %s (ID: %s) | Progress: %d/%d mins\n", 
                                m.getTitle(), m.getId(), record.getWatchedMinutes(), m.getDurationMinutes());
                            hasUnfinished = true;
                        }
                    }
                    
                    if (!hasUnfinished) {
                        System.out.println("You don't have any unfinished movies!");
                        break;
                    }
                    
                    String continueId = ValidationUtil.getString(scanner, "\nEnter Movie ID to continue watching (or type '0' to cancel): ");
                    if (continueId.equals("0")) break;
                    
                    WatchRecord currentRecord = historyController.getRecordByMovieId(continueId);
                    Movie continueMovie = movieController.findMovieById(continueId);
                    
                    if (currentRecord != null && continueMovie != null && currentRecord.getWatchedMinutes() < continueMovie.getDurationMinutes()) {
                        int remaining = continueMovie.getDurationMinutes() - currentRecord.getWatchedMinutes();
                        System.out.println("\n▶ Resuming: " + continueMovie.getTitle());
                        System.out.println("Remaining time: " + remaining + " mins.");
                        
                        int addMins = ValidationUtil.getInt(scanner, "How many minutes do you want to watch now? (1 - " + remaining + "): ", 1, remaining);
                        
                        historyController.saveOrUpdateRecord(continueId, addMins);
                        System.out.println("Watched " + addMins + " more minutes. Welcome back!");
                    } else {
                        System.out.println("Error: Invalid ID or movie is already fully watched.");
                    }
                    break;

                case 6:
                    System.out.println("\n--- Trending Categories (Top 3) ---");
                    MyLinkedList<Category> allCats = categoryController.getAllCategories();
                    MyLinkedList<Movie> allMvs = movieController.getAllMovies();
                    
                    if (allCats.isEmpty()) {
                        System.out.println("No categories available.");
                        break;
                    }

                    MyLinkedList<Category> tempCats = new MyLinkedList<>();
                    for (int i = 0; i < allCats.size(); i++) {
                        tempCats.add(allCats.get(i));
                    }

                    for (int i = 0; i < tempCats.size() - 1; i++) {
                        for (int j = 0; j < tempCats.size() - i - 1; j++) {
                            Category c1 = tempCats.get(j);
                            Category c2 = tempCats.get(j + 1);
                            
                            int views1 = 0;
                            for(int k = 0; k < allMvs.size(); k++) {
                                if(allMvs.get(k).getCategoryId().equalsIgnoreCase(c1.getId())) {
                                    views1 += allMvs.get(k).getViews();
                                }
                            }
                            
                            int views2 = 0;
                            for(int k = 0; k < allMvs.size(); k++) {
                                if(allMvs.get(k).getCategoryId().equalsIgnoreCase(c2.getId())) {
                                    views2 += allMvs.get(k).getViews();
                                }
                            }
                            
                            if (views1 < views2) {
                                tempCats.set(j, c2);
                                tempCats.set(j + 1, c1);
                            }
                        }
                    }

                    int catLimit = Math.min(3, tempCats.size());
                    boolean hasTrendingCat = false;
                    
                    for (int i = 0; i < catLimit; i++) {
                        Category c = tempCats.get(i);
                        int totalViews = 0;
                        for(int k = 0; k < allMvs.size(); k++) {
                            if(allMvs.get(k).getCategoryId().equalsIgnoreCase(c.getId())) {
                                totalViews += allMvs.get(k).getViews();
                            }
                        }
                        
                        if (totalViews > 0) {
                            System.out.printf("TOP %d: %s (ID: %s) | Total Views: %d\n", (i+1), c.getName(), c.getId(), totalViews);
                            hasTrendingCat = true;
                        }
                    }
                    
                    if (!hasTrendingCat) {
                        System.out.println("No viewing data available yet to determine trending categories.");
                    }
                    break;

                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void displayTop10Ranking() {
        System.out.println("\n=======================================================");
        System.out.println("             TOP 10 BEST MOVIE RANKING                 ");
        System.out.println("=======================================================");
        
        MyLinkedList<Movie> originalMovies = movieController.getAllMovies();
        if (originalMovies.isEmpty()) {
            System.out.println("No movies available to rank.");
            return;
        }

        MyLinkedList<Movie> tempSortList = new MyLinkedList<>();
        for (int i = 0; i < originalMovies.size(); i++) {
            tempSortList.add(originalMovies.get(i));
        }

        for (int i = 0; i < tempSortList.size() - 1; i++) {
            for (int j = 0; j < tempSortList.size() - i - 1; j++) {
                Movie m1 = tempSortList.get(j);
                Movie m2 = tempSortList.get(j + 1);
                
                if (m1.getRankingScore() < m2.getRankingScore()) {
                    tempSortList.set(j, m2);
                    tempSortList.set(j + 1, m1);
                }
            }
        }

        int limit = Math.min(10, tempSortList.size());
        for (int i = 0; i < limit; i++) {
            Movie m = tempSortList.get(i);
            System.out.printf("TOP %-2d | Score: %5.2f | %-20s (Rate: %.1f, Views: %d, Favs: %d)\n", 
                (i + 1), m.getRankingScore(), m.getTitle(), m.getRating(), m.getViews(), m.getFavorites());
        }
        System.out.println("=======================================================");
    }

    private void generateViewingReport() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n=======================================================\n");
        sb.append("                 USER VIEWING REPORT                   \n");
        sb.append("=======================================================\n");
        
        MyLinkedList<WatchRecord> repHistory = historyController.getAllHistory();
        
        if (repHistory.isEmpty()) {
            sb.append("Not enough data to generate report. Watch some movies!\n");
            System.out.print(sb.toString());
            return;
        } 
        
        int repTotalTime = 0;
        int repCompleted = 0;
        int repUnfinished = 0;

        MyLinkedList<Category> repCats = categoryController.getAllCategories();
        int[] catWatchCount = new int[repCats.size()];

        for (int i = 0; i < repHistory.size(); i++) {
            WatchRecord r = repHistory.get(i);
            repTotalTime += r.getWatchedMinutes();
            
            Movie m = movieController.findMovieById(r.getMovieId());
            if (m != null) {
                if (r.getWatchedMinutes() >= m.getDurationMinutes()) {
                    repCompleted++;
                } else {
                    repUnfinished++;
                }
                
                for (int j = 0; j < repCats.size(); j++) {
                    if (repCats.get(j).getId().equalsIgnoreCase(m.getCategoryId())) {
                        catWatchCount[j]++;
                        break;
                    }
                }
            }
        }

        int maxCatIndex = -1;
        int maxCatCount = 0;
        for (int j = 0; j < repCats.size(); j++) {
            if (catWatchCount[j] > maxCatCount) {
                maxCatCount = catWatchCount[j];
                maxCatIndex = j;
            }
        }

        String favCategoryName = (maxCatIndex != -1) ? repCats.get(maxCatIndex).getName() : "N/A";
        double completionRate = ((double) repCompleted / repHistory.size()) * 100;

        sb.append(String.format("- Total Movie Sessions    : %d\n", repHistory.size()));
        sb.append(String.format("- Total Watch Time        : %d mins\n", repTotalTime));
        sb.append(String.format("- Fully Completed Movies  : %d\n", repCompleted));
        sb.append(String.format("- Unfinished Movies       : %d\n", repUnfinished));
        sb.append(String.format("- Movie Completion Rate   : %.1f%%\n", completionRate));
        sb.append(String.format("- Most Watched Category   : %s (%d plays)\n", favCategoryName, maxCatCount));
        sb.append("=======================================================\n");

        String finalReport = sb.toString();
        System.out.print(finalReport);

        if (historyController.exportViewingReport(finalReport)) {
            System.out.println("-> Success: Report exported to data/viewing_report.txt");
        } else {
            System.out.println("-> Failed to export report.");
        }
    }
}
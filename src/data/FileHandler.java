package data;

import java.io.*;

import models.datastructures.MyLinkedList;
import models.entities.Category;
import models.entities.Movie;
import models.entities.WatchRecord;

public class FileHandler {
    private static final String DIR_PATH = "data";
    private static final String MOVIE_FILE = DIR_PATH + "/movies.txt";
    private static final String CATEGORY_FILE = DIR_PATH + "/categories.txt";
    private static final String HISTORY_FILE = DIR_PATH + "/history.txt";

    public FileHandler() {
        File directory = new File(DIR_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public MyLinkedList<Category> loadCategories() {
        MyLinkedList<Category> categories = new MyLinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CATEGORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|"); 
                if (parts.length == 2) {
                    categories.add(new Category(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
        }
        return categories;
    }

    public void saveCategories(MyLinkedList<Category> categories) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CATEGORY_FILE))) {
            for (int i = 0; i < categories.size(); i++) {
                Category cat = categories.get(i);
                writer.write(cat.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file Categories: " + e.getMessage());
        }
    }

    public MyLinkedList<Movie> loadMovies() {
        MyLinkedList<Movie> movies = new MyLinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 10) {
                    try {
                        Movie m = new Movie(
                            parts[0], parts[1], parts[2], parts[3], parts[4],
                            Double.parseDouble(parts[5]), Integer.parseInt(parts[6]), Integer.parseInt(parts[7])
                        );
                        m.setViews(Integer.parseInt(parts[8]));
                        m.setFavorites(Integer.parseInt(parts[9]));
                        movies.add(m);
                    } catch (NumberFormatException ex) {
                    }
                }
            }
        } catch (IOException e) {}
        return movies;
    }

    public void saveMovies(MyLinkedList<Movie> movies) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MOVIE_FILE))) {
            for (int i = 0; i < movies.size(); i++) {
                Movie movie = movies.get(i);
                writer.write(movie.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file Movies: " + e.getMessage());
        }
    }

    public MyLinkedList<WatchRecord> loadHistory() {
        MyLinkedList<WatchRecord> history = new MyLinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    try {
                        history.add(new WatchRecord(parts[0], Integer.parseInt(parts[1]), Long.parseLong(parts[2])));
                    } catch (NumberFormatException ex) {}
                }
            }
        } catch (IOException e) {}
        return history;
    }

    public void saveHistory(MyLinkedList<WatchRecord> history) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (int i = 0; i < history.size(); i++) {
                WatchRecord record = history.get(i);
                writer.write(record.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file History: " + e.getMessage());
        }
    }

    public boolean exportReportToFile(String content) {
        String reportPath = DIR_PATH + "/viewing_report.txt"; 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportPath))) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing report file: " + e.getMessage());
            return false;
        }
    }
}
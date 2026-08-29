package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Category> loadCategories() {
        List<Category> categories = new ArrayList<>();
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

    public void saveCategories(List<Category> categories) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CATEGORY_FILE))) {
            for (Category cat : categories) {
                writer.write(cat.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file Categories: " + e.getMessage());
        }
    }

    public List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();
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

    public void saveMovies(List<Movie> movies) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MOVIE_FILE))) {
            for (Movie movie : movies) {
                writer.write(movie.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file Movies: " + e.getMessage());
        }
    }

    public List<WatchRecord> loadHistory() {
        List<WatchRecord> history = new ArrayList<>();
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

    public void saveHistory(List<WatchRecord> history) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (WatchRecord record : history) {
                writer.write(record.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file History: " + e.getMessage());
        }
    }
}
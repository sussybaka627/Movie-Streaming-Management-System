package storage;

import model.Category;
import model.Movie;
import model.WatchRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String DIR_PATH = "data";
    private static final String MOVIE_FILE = DIR_PATH + "/movies.txt";
    private static final String CATEGORY_FILE = DIR_PATH + "/categories.txt";
    private static final String HISTORY_FILE = DIR_PATH + "/history.txt";

    public FileStorage() {
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
}
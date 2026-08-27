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
}
package app;

import model.Category;
import model.Movie;
import model.WatchRecord;
import storage.FileStorage;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileStorage storage = new FileStorage();

        System.out.println("Test");

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("C01", "Action"));
        categories.add(new Category("C02", "Comedy"));
        categories.add(new Category("C03", "Sci-Fi"));
        
        storage.saveCategories(categories);
        System.out.println("Đã ghi file categories.txt thành công!");

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("M01", "Die Hard", "John McTiernan", "Bruce Willis", "C01", 8.2, 1988, 132));
        movies.add(new Movie("M02", "Dumb and Dumber", "Peter Farrelly", "Jim Carrey", "C02", 7.3, 1994, 107));
        movies.add(new Movie("M03", "Interstellar", "Christopher Nolan", "Matthew McConaughey", "C03", 8.7, 2014, 169));
        movies.get(2).setViews(500);
        movies.get(2).setFavorites(120);
        
        storage.saveMovies(movies);
        System.out.println("Đã ghi file movies.txt thành công!");

        List<WatchRecord> history = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        history.add(new WatchRecord("M01", 45, currentTime));
        history.add(new WatchRecord("M03", 169, currentTime - 86400000));
        
        storage.saveHistory(history);
        System.out.println("Đã ghi file history.txt thành công!");

        System.out.println("\n=== BẮT ĐẦU TEST ĐỌC DỮ LIỆU TỪ FILE ===");

        System.out.println("\n[ Danh sách Thể loại ]");
        List<Category> loadedCategories = storage.loadCategories();
        for (Category c : loadedCategories) {
            System.out.println(c.toString());
        }

        System.out.println("\n[ Danh sách Phim ]");
        List<Movie> loadedMovies = storage.loadMovies();
        for (Movie m : loadedMovies) {
            System.out.println(m.toString() + " | Score: " + String.format("%.2f", m.getRankingScore()));
        }

        System.out.println("\n[ Lịch sử xem ]");
        List<WatchRecord> loadedHistory = storage.loadHistory();
        for (WatchRecord w : loadedHistory) {
            System.out.println("Phim ID: " + w.getMovieId() + " | Đã xem: " + w.getWatchedMinutes() + " phút | Lần cuối: " + w.getLastWatchTime());
        }
    }
}
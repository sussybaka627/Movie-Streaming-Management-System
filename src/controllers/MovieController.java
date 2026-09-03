package controllers;

import data.FileHandler;
import models.datastructures.MyLinkedList;
import models.entities.Movie;

public class MovieController {
    private MyLinkedList<Movie> movies;
    private FileHandler fileHandler;

    public MovieController() {
        this.fileHandler = new FileHandler();
        this.movies = fileHandler.loadMovies();
        if (this.movies == null) {
            this.movies = new MyLinkedList<>();
        }
    }

    public MyLinkedList<Movie> getAllMovies() {
        return movies;
    }

    public boolean addMovie(String id, String title, String director, String actor, 
                            String categoryId, double rating, int releaseYear, int durationMinutes) {
        if (findMovieById(id) != null) {
            return false; 
        }
        
        Movie newMovie = new Movie(id, title, director, actor, categoryId, rating, releaseYear, durationMinutes);
        movies.add(newMovie);
        fileHandler.saveMovies(movies);
        return true;
    }

    public boolean updateMovie(String id, String title, String director, String actor, 
                               String categoryId, double rating, int releaseYear, int durationMinutes) {
        Movie movie = findMovieById(id);
        if (movie == null) {
            return false;
        }

        movie.setTitle(title);
        movie.setDirector(director);
        movie.setActor(actor);
        movie.setCategoryId(categoryId);
        movie.setRating(rating);
        movie.setReleaseYear(releaseYear);
        movie.setDurationMinutes(durationMinutes);
        
        fileHandler.saveMovies(movies);
        return true;
    }

    public boolean deleteMovie(String id) {
        Movie movie = findMovieById(id);
        if (movie == null) {
            return false; 
        }
        
        movies.remove(movie);
        fileHandler.saveMovies(movies);
        return true;
    }

    public Movie findMovieById(String id) {
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getId().equalsIgnoreCase(id)) {
                return movie;
            }
        }
        return null;
    }

    public MyLinkedList<Movie> searchMovies(String keyword) {
        MyLinkedList<Movie> searchResults = new MyLinkedList<>();
        String lowerKeyword = keyword.toLowerCase(); 

        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getTitle().toLowerCase().contains(lowerKeyword) ||
                movie.getDirector().toLowerCase().contains(lowerKeyword) ||
                movie.getActor().toLowerCase().contains(lowerKeyword)) {
                
                searchResults.add(movie);
            }
        }
        return searchResults;
    }

    public void sortMoviesByRating() {
        int n = movies.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Movie m1 = movies.get(j);
                Movie m2 = movies.get(j + 1);
                
                if (m1.getRating() < m2.getRating()) {
                    movies.set(j, m2);
                    movies.set(j + 1, m1);
                }
            }
        }
        fileHandler.saveMovies(movies);
    }

    public void sortMoviesByYear() {
        int n = movies.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Movie m1 = movies.get(j);
                Movie m2 = movies.get(j + 1);
                
                if (m1.getReleaseYear() < m2.getReleaseYear()) {
                    movies.set(j, m2);
                    movies.set(j + 1, m1);
                }
            }
        }
        fileHandler.saveMovies(movies);
    }

    public MyLinkedList<Movie> getFavoriteMovies() {
        MyLinkedList<Movie> favList = new MyLinkedList<>();
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getFavorites() > 0) {
                favList.add(movie);
            }
        }
        return favList;
    }

    public boolean toggleFavorite(String id, boolean isAdding) {
        Movie movie = findMovieById(id);
        if (movie == null) {
            return false;
        }
        
        if (isAdding) {
            movie.setFavorites(movie.getFavorites() + 1);
        } else {
            if (movie.getFavorites() > 0) {
                movie.setFavorites(movie.getFavorites() - 1);
            }
        }
        
        fileHandler.saveMovies(movies);
        return true;
    }

    public MyLinkedList<Movie> getMoviesByCategory(String categoryId) {
        MyLinkedList<Movie> result = new MyLinkedList<>();
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            if (m.getCategoryId().equalsIgnoreCase(categoryId)) {
                result.add(m);
            }
        }
        return result;
    }

    public void saveAllMoviesData() {
        fileHandler.saveMovies(movies);
    }
}
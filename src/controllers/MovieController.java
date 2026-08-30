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
}
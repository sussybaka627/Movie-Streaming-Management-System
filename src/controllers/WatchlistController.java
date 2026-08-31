package controllers;

import models.datastructures.MyQueue;
import models.datastructures.MyStack;
import models.entities.Movie;

public class WatchlistController {
    private MyQueue<Movie> watchlist;
    private MyStack<Movie> undoStack;

    public WatchlistController() {
        this.watchlist = new MyQueue<>();
        this.undoStack = new MyStack<>();
    }

    public void addMovieToWatchlist(Movie movie) {
        watchlist.enqueue(movie);
    }

    public Movie watchNext() {
        if (watchlist.isEmpty()) {
            return null;
        }
        Movie nextMovie = watchlist.dequeue();
        undoStack.push(nextMovie);
        return nextMovie;
    }

    public Movie undoLastWatch() {
        if (undoStack.isEmpty()) {
            return null;
        }
        Movie lastWatched = undoStack.pop();
        watchlist.enqueue(lastWatched);
        return lastWatched;
    }

    public void displayWatchlist() {
        if (watchlist.isEmpty()) {
            System.out.println("Watchlist is currently empty.");
            return;
        }
        
        System.out.println("\n--- Current Watchlist ---");
        int count = 1;
        int size = watchlist.size();
        
        for (int i = 0; i < size; i++) {
            Movie m = watchlist.dequeue();
            System.out.println(count++ + ". " + m.getTitle() + " (ID: " + m.getId() + ")");
            watchlist.enqueue(m);
        }
        System.out.println("-------------------------");
    }
}

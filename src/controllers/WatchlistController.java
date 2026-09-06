package controllers;

import data.FileHandler;
import models.datastructures.MyLinkedList;
import models.datastructures.MyQueue;
import models.datastructures.MyStack;
import models.entities.Movie;

public class WatchlistController {
    private String currentUser;
    private MyQueue<Movie> watchlist;
    private MyStack<Movie> undoStack;
    private FileHandler fileHandler;
    private MovieController movieController;

    public WatchlistController(String username, FileHandler fileHandler, MovieController movieController) {
        this.currentUser = username;
        this.fileHandler = fileHandler;
        this.movieController = movieController;
        this.watchlist = new MyQueue<>();
        this.undoStack = new MyStack<>();
        loadUserWatchlist();
    }

    private void loadUserWatchlist() {
        MyLinkedList<String> allData = fileHandler.loadAllWatchlists();
        for (int i = 0; i < allData.size(); i++) {
            String[] parts = allData.get(i).split("\\|");
            if (parts.length == 2 && parts[0].equals(currentUser)) {
                Movie m = movieController.findMovieById(parts[1]);
                if (m != null) {
                    watchlist.enqueue(m);
                }
            }
        }
    }

    private void saveUserWatchlist() {
        MyLinkedList<String> allData = fileHandler.loadAllWatchlists();
        MyLinkedList<String> newData = new MyLinkedList<>();
        for (int i = 0; i < allData.size(); i++) {
            String[] parts = allData.get(i).split("\\|");
            if (parts.length == 2 && !parts[0].equals(currentUser)) {
                newData.add(allData.get(i));
            }
        }

        int size = watchlist.size();
        for (int i = 0; i < size; i++) {
            Movie m = watchlist.dequeue();
            newData.add(currentUser + "|" + m.getId());
            watchlist.enqueue(m);
        }
        fileHandler.saveAllWatchlists(newData);
    }

    public boolean addMovieToWatchlist(Movie movie) {
        boolean isExist = false;
        int size = watchlist.size();
        for (int i = 0; i < size; i++) {
            Movie m = watchlist.dequeue();
            if (m.getId().equals(movie.getId())) {
                isExist = true;
            }
            watchlist.enqueue(m);
        }
        
        if (!isExist) {
            watchlist.enqueue(movie);
            saveUserWatchlist();
            return true;
        }
        return false;
    }

    public Movie watchNext() {
        if (watchlist.isEmpty()) {
            return null;
        }
        Movie nextMovie = watchlist.dequeue();
        undoStack.push(nextMovie);
        saveUserWatchlist();
        return nextMovie;
    }

    public Movie undoLastWatch() {
        if (undoStack.isEmpty()) {
            return null;
        }
        Movie lastWatched = undoStack.pop();
        watchlist.enqueue(lastWatched);
        saveUserWatchlist();
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
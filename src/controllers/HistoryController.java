package controllers;

import data.FileHandler;
import models.datastructures.MyLinkedList;
import models.entities.WatchRecord;

public class HistoryController {
    private MyLinkedList<WatchRecord> history;
    private FileHandler fileHandler;

    public HistoryController() {
        this.fileHandler = new FileHandler();
        this.history = fileHandler.loadHistory();
        if (this.history == null) {
            this.history = new MyLinkedList<>();
        }
    }

    public MyLinkedList<WatchRecord> getAllHistory() {
        return history;
    }

    public int getTotalWatchTime(String username) {
        int totalMinutes = 0;
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).getUsername().equals(username)) {
                totalMinutes += history.get(i).getWatchedMinutes();
            }
        }
        return totalMinutes;
    }

    public boolean exportViewingReport(String reportContent) {
        return fileHandler.exportReportToFile(reportContent);
    }

    public MyLinkedList<WatchRecord> getHistoryByUser(String username) {
        MyLinkedList<WatchRecord> userHistory = new MyLinkedList<>();
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).getUsername().equals(username)) {
                userHistory.add(history.get(i));
            }
        }
        return userHistory;
    }

    public WatchRecord getRecordByUserAndMovie(String username, String movieId) {
        for (int i = 0; i < history.size(); i++) {
            WatchRecord r = history.get(i);
            if (r.getUsername().equals(username) && r.getMovieId().equalsIgnoreCase(movieId)) {
                return r;
            }
        }
        return null;
    }

    public void saveOrUpdateRecord(String username, String movieId, int minutesWatched) {
        WatchRecord existingRecord = getRecordByUserAndMovie(username, movieId);
        long currentTime = System.currentTimeMillis();

        if (existingRecord != null) {
            existingRecord.setWatchedMinutes(existingRecord.getWatchedMinutes() + minutesWatched);
            existingRecord.setLastWatchTime(currentTime);
        } else {
            history.add(new WatchRecord(username, movieId, minutesWatched, currentTime));
        }
        fileHandler.saveHistory(history);
    }
}
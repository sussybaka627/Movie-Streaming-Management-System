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

    public void addWatchRecord(String movieId, int watchedMinutes) {
        long currentTime = System.currentTimeMillis();
        WatchRecord record = new WatchRecord(movieId, watchedMinutes, currentTime);
        history.add(record);
        fileHandler.saveHistory(history);
    }

    public int getTotalWatchTime() {
        int totalMinutes = 0;
        for (int i = 0; i < history.size(); i++) {
            totalMinutes += history.get(i).getWatchedMinutes();
        }
        return totalMinutes;
    }

    public WatchRecord getRecordByMovieId(String movieId) {
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).getMovieId().equalsIgnoreCase(movieId)) {
                return history.get(i);
            }
        }
        return null;
    }

    public void saveOrUpdateRecord(String movieId, int minutesWatched) {
        WatchRecord existingRecord = getRecordByMovieId(movieId);
        long currentTime = System.currentTimeMillis();

        if (existingRecord != null) {
            existingRecord.setWatchedMinutes(existingRecord.getWatchedMinutes() + minutesWatched);
            existingRecord.setLastWatchTime(currentTime);
        } else {
            WatchRecord newRecord = new WatchRecord(movieId, minutesWatched, currentTime);
            history.add(newRecord);
        }
        fileHandler.saveHistory(history);
    }

    public boolean exportViewingReport(String reportContent) {
        return fileHandler.exportReportToFile(reportContent);
    }
}
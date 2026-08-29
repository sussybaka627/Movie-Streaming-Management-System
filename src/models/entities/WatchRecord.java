package models.entities;

public class WatchRecord {
    private String movieId;
    private int watchedMinutes;
    private long lastWatchTime;

    public WatchRecord() {}

    public WatchRecord(String movieId, int watchedMinutes, long lastWatchTime) {
        this.movieId = movieId;
        this.watchedMinutes = watchedMinutes;
        this.lastWatchTime = lastWatchTime;
    }

    public String toDataString() {
        return movieId + "|" + watchedMinutes + "|" + lastWatchTime;
    }

    public String getMovieId() { 
        return movieId; 
    }

    public void setMovieId(String movieId) { 
        this.movieId = movieId; 
    }

    public int getWatchedMinutes() { 
        return watchedMinutes; 
    }

    public void setWatchedMinutes(int watchedMinutes) { 
        this.watchedMinutes = watchedMinutes; 
    }

    public long getLastWatchTime() { 
        return lastWatchTime; 
    }

    public void setLastWatchTime(long lastWatchTime) { 
        this.lastWatchTime = lastWatchTime; 
    }
}

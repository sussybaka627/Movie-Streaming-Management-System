package models.entities;

public class WatchRecord {
    private String username;
    private String movieId;
    private int watchedMinutes;
    private long lastWatchTime;

    public WatchRecord() {}

    public WatchRecord(String username, String movieId, int watchedMinutes, long lastWatchTime) {
        this.username = username;
        this.movieId = movieId;
        this.watchedMinutes = watchedMinutes;
        this.lastWatchTime = lastWatchTime;
    }

    public String toDataString() {
        return username + "|" + movieId + "|" + watchedMinutes + "|" + lastWatchTime;
    }

    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
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

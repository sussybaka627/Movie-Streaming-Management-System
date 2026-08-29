package model;

public class Movie {
    private String id;
    private String title;
    private String director;
    private String actor;
    private String categoryId;
    private double rating;
    private int releaseYear;
    private int durationMinutes;
    private int views;
    private int favorites;

    public Movie() {}

    public Movie(String id, String title, String director, String actor, 
                 String categoryId, double rating, int releaseYear, int durationMinutes) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.actor = actor;
        this.categoryId = categoryId;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.durationMinutes = durationMinutes;
        this.views = 0;    
        this.favorites = 0;  
    }

    public double getRankingScore() {
        return (this.rating * 10 * 0.4) + (this.views * 0.4) + (this.favorites * 0.2);
    }

    public String toDataString() {
        return id + "|" + title + "|" + director + "|" + actor + "|" + 
               categoryId + "|" + rating + "|" + releaseYear + "|" + 
               durationMinutes + "|" + views + "|" + favorites;
    }

    public String getId() { 
        return id; 
    }

    public String getTitle() { 
        return title; 
    }

    public String getDirector() { 
        return director; 
    }

    public String getActor() { 
        return actor; 
    }

    public String getCategoryId() { 
        return categoryId; 
    }

    public double getRating() { 
        return rating; 
    }

    public int getReleaseYear() { 
        return releaseYear; 
    }

    public int getDurationMinutes() { 
        return durationMinutes; 
    
    }

    public int getViews() { 
        return views; 
    }

    public int getFavorites() { 
        return favorites; 
    }


    public void setId(String id) { 
        this.id = id;
    }

    public void setTitle(String title) { 
        this.title = title; 
    }

    public void setDirector(String director) { 
        this.director = director; 
    }

    public void setActor(String actor) { 
        this.actor = actor; 
    }

    public void setCategoryId(String categoryId) { 
        this.categoryId = categoryId; 
    }

    public void setRating(double rating) { 
        this.rating = rating; 
    }

    public void setReleaseYear(int releaseYear) { 
        this.releaseYear = releaseYear; 
    }

    public void setDurationMinutes(int durationMinutes) { 
        this.durationMinutes = durationMinutes; 
    }

    public void setViews(int views) { 
        this.views = views; 
    }

    public void setFavorites(int favorites) { 
        this.favorites = favorites; 
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %-5s | %-25s | Dir: %-15s | Year: %-4d | Rate: %4.1f | Views: %-5d | Favs: %-5d",
            id, title, director, releaseYear, rating, views, favorites
        );
    }
}
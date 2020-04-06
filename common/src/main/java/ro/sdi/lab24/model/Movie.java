package ro.sdi.lab24.model;

import java.io.Serializable;

public class Movie extends Entity<Integer> implements Serializable {
    private String name;
    private String genre;
    private int rating;

    public Movie(int id, String name, String genre, int rating) {
        super(id);
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    public String getName()
    {
        return name;
    }

    public String getGenre()
    {
        return genre;
    }

    public int getRating()
    {
        return rating;
    }

    @Override
    public String toString()
    {
        return String.format("Movie[%d, %s, %s, %d]", id, name, genre, rating);
    }
}

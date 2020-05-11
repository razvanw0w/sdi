package ro.sdi.lab24.core.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@javax.persistence.Entity
public class Movie extends Entity<Integer> implements Serializable {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String genre;

    @NotNull
    @Min(0)
    @Max(100)
    private int rating;

    public Movie() {
        super(0);
    }

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

    public String getGenre() {
        return genre;
    }

    public int getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("Movie[%d, %s, %s, %d]", id, name, genre, rating);
    }
}

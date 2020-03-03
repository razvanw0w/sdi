package ro.sdi.lab24.model;

public class Movie extends Entity<Integer>
{
    private String name;
    private String genre;
    private int rating;

    public Movie(int id, String name)
    {
        super(id);
        this.name = name;
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

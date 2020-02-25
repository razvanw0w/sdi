package ro.sdi.lab24.model;

public class Movie extends Entity<Integer>
{
    String name;

    public Movie(int id, String name)
    {
        super(id);
        this.name = name;
    }

    @Override
    public String toString()
    {
        return String.format("Movie[%d, %s]", id, name);
    }
}

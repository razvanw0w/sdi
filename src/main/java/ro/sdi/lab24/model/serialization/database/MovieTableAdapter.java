package ro.sdi.lab24.model.serialization.database;

import ro.sdi.lab24.model.Movie;

import java.sql.Connection;
import java.util.List;

public class MovieTableAdapter implements TableAdapter<Movie>
{
    @Override
    public void insert(Movie entity, Connection connection)
    {
        //TODO
    }

    @Override
    public List<Movie> read(Connection connection)
    {
        return null;//TODO
    }

    @Override
    public void update(Movie entity, Connection connection)
    {
        //TODO
    }

    @Override
    public void delete(Movie entity, Connection connection)
    {
        //TODO
    }
}

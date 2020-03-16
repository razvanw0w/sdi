package ro.sdi.lab24.model.serialization.database;

import ro.sdi.lab24.model.Rental;

import java.sql.Connection;
import java.util.List;

public class RentalTableAdapter implements TableAdapter<Rental>
{
    @Override
    public void insert(Rental entity, Connection connection)
    {
        //TODO
    }

    @Override
    public List<Rental> read(Connection connection)
    {
        return null;//TODO
    }

    @Override
    public void update(Rental entity, Connection connection)
    {
        //TODO
    }

    @Override
    public void delete(Rental entity, Connection connection)
    {
        //TODO
    }
}

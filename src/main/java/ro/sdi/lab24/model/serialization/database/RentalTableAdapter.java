package ro.sdi.lab24.model.serialization.database;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import ro.sdi.lab24.model.Rental;

public class RentalTableAdapter implements TableAdapter<Rental.RentalID, Rental>
{
    @Override
    public void insert(Rental entity, Connection connection)
    {
        //TODO
    }

    @Override
    public List<Rental> readAll(Connection connection)
    {
        return null;//TODO
    }

    @Override
    public Optional<Rental> read(Rental.RentalID rentalID, Connection connection)
    {
        return Optional.empty();//TODO
    }

    @Override
    public void update(Rental entity, Connection connection)
    {
        //TODO
    }

    @Override
    public void delete(Rental.RentalID id, Connection connection)
    {
        //TODO
    }
}

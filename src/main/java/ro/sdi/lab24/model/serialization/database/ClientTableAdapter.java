package ro.sdi.lab24.model.serialization.database;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import ro.sdi.lab24.model.Client;

public class ClientTableAdapter implements TableAdapter<Integer, Client>
{

    @Override
    public void insert(Client entity, Connection connection)
    {
        //TODO
    }

    @Override
    public List<Client> readAll(Connection connection)
    {
        return null;//TODO
    }

    @Override
    public Optional<Client> read(Integer integer, Connection connection)
    {
        return Optional.empty();//TODO
    }

    @Override
    public void update(Client entity, Connection connection)
    {
        //TODO
    }

    @Override
    public void delete(Integer id, Connection connection)
    {
        //TODO
    }
}

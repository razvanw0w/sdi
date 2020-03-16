package ro.sdi.lab24.model.serialization.database;

import ro.sdi.lab24.model.Client;

import java.sql.Connection;
import java.util.List;

public class ClientTableAdapter implements TableAdapter<Client>
{

    @Override
    public void insert(Client entity, Connection connection)
    {
        //TODO
    }

    @Override
    public List<Client> read(Connection connection)
    {
        return null;//TODO
    }

    @Override
    public void update(Client entity, Connection connection)
    {
        //TODO
    }

    @Override
    public void delete(Client entity, Connection connection)
    {
        //TODO
    }
}

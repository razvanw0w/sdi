package ro.sdi.lab24.core.model.serialization.csv;

import ro.sdi.lab24.core.exception.ParsingException;
import ro.sdi.lab24.core.model.Client;

import java.util.Optional;

public class ClientCSVSerializer implements CSVSerializer<Client>
{
    @Override
    public String serialize(Client client)
    {
        return String.format("%d,%s", client.getId(), client.getName());
    }

    @Override
    public Client deserialize(String string)
    {
        String[] line = string.split(",");
        Optional.of(line)
                .filter(strings -> strings.length == 2)
                .orElseThrow(() -> new ParsingException("Client csv string cannot be parsed"));
        try
        {
            return new Client(Integer.parseInt(line[0]), line[1]);
        }
        catch (NumberFormatException e)
        {
            throw new ParsingException("Client csv string cannot be parsed");
        }
    }
}

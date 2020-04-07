package ro.sdi.lab.common.model.serialization.csv;

import ro.sdi.lab.common.serialization.CSVSerializer;
import ro.sdi.lab.common.exception.ParsingException;
import ro.sdi.lab.common.model.Client;
import ro.sdi.lab.common.model.dto.ClientGenre;

import java.util.Optional;

public class ClientGenreCSVSerializer implements CSVSerializer<ClientGenre>
{
    @Override
    public String serialize(ClientGenre entity) {
        ClientCSVSerializer clientCSVSerializer = new ClientCSVSerializer();
        String genre = "";
        if (entity.getGenre() == "")
            genre = ", ";
        else
            genre = String.format(",%s", entity.getGenre());

        return clientCSVSerializer.serialize(entity.getClient()) + genre;
    }

    @Override
    public ClientGenre deserialize(String string) {
        String[] line = string.split(",");
        Optional.of(line)
                .filter(strings -> strings.length == 3)
                .orElseThrow(() -> new ParsingException("ClientGenre csv string cannot be parsed"));
        try {
            return new ClientGenre(new Client(Integer.parseInt(line[0]), line[1]), line[2]);
        } catch (NumberFormatException e) {
            throw new ParsingException("ClientGenre csv string cannot be parsed");
        }
    }
}

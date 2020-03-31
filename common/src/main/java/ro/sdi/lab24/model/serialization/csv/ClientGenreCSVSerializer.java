package ro.sdi.lab24.model.serialization.csv;

import ro.sdi.lab24.exception.ParsingException;
import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.dto.ClientGenre;
import ro.sdi.lab24.serialization.CSVSerializer;

import java.util.Optional;

public class ClientGenreCSVSerializer implements CSVSerializer<ClientGenre> {
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

package ro.sdi.lab24.model.serialization.csv;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import ro.sdi.lab24.exception.ParsingException;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.serialization.CSVSerializer;

public class RentalCSVSerializer implements CSVSerializer<Rental>
{
    @Override
    public String serialize(Rental rental)
    {
        return String.format(
                "%d,%d,%s",
                rental.getId().getMovieId(),
                rental.getId().getClientId(),
                rental.getTime()
        );
    }

    @Override
    public Rental deserialize(String string)
    {
        String[] line = string.split(",");
        Optional.of(line)
                .filter(strings -> strings.length == 3)
                .orElseThrow(() -> new ParsingException("Rental csv string cannot be parsed"));
        try
        {
            return new Rental(
                    Integer.parseInt(line[0]),
                    Integer.parseInt(line[1]),
                    LocalDateTime.parse(line[2])
            );
        }
        catch (NumberFormatException | DateTimeParseException e)
        {
            throw new ParsingException("Rental csv string cannot be parsed");
        }
    }
}

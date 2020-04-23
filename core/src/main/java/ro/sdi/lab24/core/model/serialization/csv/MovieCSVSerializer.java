package ro.sdi.lab24.core.model.serialization.csv;

import ro.sdi.lab24.core.exception.ParsingException;
import ro.sdi.lab24.core.model.Movie;

import java.util.Optional;

public class MovieCSVSerializer implements CSVSerializer<Movie>
{
    @Override
    public String serialize(Movie movie)
    {
        return String.format(
                "%d,%s,%s,%d",
                movie.getId(),
                movie.getName(),
                movie.getGenre(),
                movie.getRating()
        );
    }

    @Override
    public Movie deserialize(String string)
    {
        String[] line = string.split(",");
        Optional.of(line)
                .filter(strings -> strings.length == 4)
                .orElseThrow(() -> new ParsingException("Movie csv string cannot be parsed"));
        try
        {
            return new Movie(
                    Integer.parseInt(line[0]),
                    line[1],
                    line[2],
                    Integer.parseInt(line[3])
            );
        }
        catch (NumberFormatException e)
        {
            throw new ParsingException("Movie csv string cannot be parsed");
        }
    }
}

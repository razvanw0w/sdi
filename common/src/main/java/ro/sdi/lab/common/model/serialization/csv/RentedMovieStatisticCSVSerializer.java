package ro.sdi.lab.common.model.serialization.csv;

import ro.sdi.lab.common.exception.ParsingException;
import ro.sdi.lab.common.model.dto.RentedMovieStatistic;
import ro.sdi.lab.common.serialization.CSVSerializer;

import java.util.Optional;

public class RentedMovieStatisticCSVSerializer implements CSVSerializer<RentedMovieStatistic> {
    @Override
    public String serialize(RentedMovieStatistic entity) {
        return String.format("%s,%d", entity.getMovieName(), entity.getNumberOfRentals());
    }

    @Override
    public RentedMovieStatistic deserialize(String string) {
        String[] line = string.split(",");
        Optional.of(line)
                .filter(strings -> strings.length == 2)
                .orElseThrow(() -> new ParsingException("RentedMovieStatistic csv string cannot be parsed"));
        try {
            return new RentedMovieStatistic(line[0], Long.parseLong(line[1]));
        } catch (NumberFormatException e) {
            throw new ParsingException("RentedMovieStatistic csv string cannot be parsed");
        }
    }
}

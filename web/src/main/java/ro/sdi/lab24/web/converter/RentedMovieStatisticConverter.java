package ro.sdi.lab24.web.converter;

import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.controller.dto.RentedMovieStatistic;
import ro.sdi.lab24.web.dto.RentedMovieStatisticDTO;

@Component
public class RentedMovieStatisticConverter implements Converter<RentedMovieStatistic, RentedMovieStatisticDTO> {
    @Override
    public RentedMovieStatistic toModel(RentedMovieStatisticDTO rentedMovieStatisticDTO) {
        return new RentedMovieStatistic(
                rentedMovieStatisticDTO.getMovieName(),
                rentedMovieStatisticDTO.getNumberOfRentals()
        );
    }

    @Override
    public RentedMovieStatisticDTO toDTO(RentedMovieStatistic rentedMovieStatistic) {
        return RentedMovieStatisticDTO.builder()
                .movieName(rentedMovieStatistic.getMovieName())
                .numberOfRentals(rentedMovieStatistic.getNumberOfRentals())
                .build();
    }
}

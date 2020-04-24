package ro.sdi.lab24.web.converter;

import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Rental;
import ro.sdi.lab24.web.controller.RentalRestController;
import ro.sdi.lab24.web.dto.RentalDTO;

import java.time.LocalDateTime;

@Component
public class RentalConverter implements Converter<Rental, RentalDTO> {
    @Override
    public Rental toModel(RentalDTO rentalDTO) {
        return new Rental(
                rentalDTO.getMovieId(),
                rentalDTO.getClientId(),
                LocalDateTime.parse(rentalDTO.getTime(), RentalRestController.formatter)
        );
    }

    @Override
    public RentalDTO toDTO(Rental rental) {
        return RentalDTO.builder()
                .movieId(rental.getId().getMovieId())
                .clientId(rental.getId().getClientId())
                .time(RentalRestController.formatter.format(rental.getTime()))
                .build();
    }
}

package ro.sdi.lab24.web.converter;

import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.model.Rental;
import ro.sdi.lab24.web.controller.RentalRestController;
import ro.sdi.lab24.web.dto.RentalDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Component
public class RentalConverter implements Converter<Rental, RentalDTO> {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Rental toModel(RentalDTO rentalDTO) {
        Rental rental = Rental.builder()
                .movie(entityManager.getReference(Movie.class, rentalDTO.getMovieId()))
                .client(entityManager.getReference(Client.class, rentalDTO.getClientId()))
                .time(LocalDateTime.parse(rentalDTO.getTime(), RentalRestController.formatter))
                .build();
        rental.setId(rentalDTO.getRentalId());
        return rental;
    }

    @Override
    public RentalDTO toDTO(Rental rental) {
        return RentalDTO.builder()
                .rentalId(rental.getId())
                .movieId(rental.getMovie().getId())
                .clientId(rental.getClient().getId())
                .time(RentalRestController.formatter.format(rental.getTime()))
                .build();
    }
}

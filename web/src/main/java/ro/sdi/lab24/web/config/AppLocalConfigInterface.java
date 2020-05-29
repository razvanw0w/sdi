package ro.sdi.lab24.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.model.Rental;
import ro.sdi.lab24.core.model.copyadapters.ClientCopyAdapter;
import ro.sdi.lab24.core.model.copyadapters.CopyAdapter;
import ro.sdi.lab24.core.model.copyadapters.MovieCopyAdapter;
import ro.sdi.lab24.core.model.copyadapters.RentalCopyAdapter;
import ro.sdi.lab24.core.validation.ClientValidator;
import ro.sdi.lab24.core.validation.MovieValidator;
import ro.sdi.lab24.core.validation.RentalValidator;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface AppLocalConfigInterface {
    /**
     * Enables placeholders usage with SpEL expressions.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    default CopyAdapter<Client> clientCopyAdapter() {
        return new ClientCopyAdapter();
    }

    @Bean
    default CopyAdapter<Movie> movieCopyAdapter() {
        return new MovieCopyAdapter();
    }

    @Bean
    default CopyAdapter<Rental> rentalCopyAdapter() {
        return new RentalCopyAdapter();
    }

    @Bean
    default RentalValidator rentalValidator() {
        return new RentalValidator();
    }

    @Bean
    default MovieValidator movieValidator() {
        return new MovieValidator();
    }

    @Bean
    default ClientValidator clientValidator() {
        return new ClientValidator();
    }

    @Bean
    default ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    default DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    }
}

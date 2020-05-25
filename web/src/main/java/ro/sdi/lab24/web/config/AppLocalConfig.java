package ro.sdi.lab24.web.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ro.sdi.lab24.core.config.JPAConfig;
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

@Configuration
@ComponentScan({"ro.sdi.lab24.core"})
@Import({JPAConfig.class})
@PropertySources({@PropertySource(value = "classpath:local/db.properties"),
})
public class AppLocalConfig {
    /**
     * Enables placeholders usage with SpEL expressions.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    CopyAdapter<Client> clientCopyAdapter() {
        return new ClientCopyAdapter();
    }

    @Bean
    CopyAdapter<Movie> movieCopyAdapter() {
        return new MovieCopyAdapter();
    }

    @Bean
    CopyAdapter<Rental> rentalCopyAdapter() {
        return new RentalCopyAdapter();
    }

    @Bean
    RentalValidator rentalValidator() {
        return new RentalValidator();
    }

    @Bean
    MovieValidator movieValidator() {
        return new MovieValidator();
    }

    @Bean
    ClientValidator clientValidator() {
        return new ClientValidator();
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    }
}

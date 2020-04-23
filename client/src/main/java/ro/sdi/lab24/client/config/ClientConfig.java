package ro.sdi.lab24.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import ro.sdi.lab24.core.config.CoreConfig;

/**
 * Created by radu.
 */
@Configuration
@Import({CoreConfig.class})
public class ClientConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

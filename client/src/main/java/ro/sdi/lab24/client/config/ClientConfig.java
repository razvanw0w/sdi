package ro.sdi.lab24.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by radu.
 */
@Configuration
@ComponentScan({"ro.sdi.lab24.client.controller", "ro.sdi.lab24.client.view", "ro.sdi.lab24.web.converter"})
public class ClientConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

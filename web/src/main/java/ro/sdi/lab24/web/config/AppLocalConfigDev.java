package ro.sdi.lab24.web.config;

import org.springframework.context.annotation.*;
import ro.sdi.lab24.core.config.JPAConfig;

@Configuration
@Profile("dev")
@ComponentScan({"ro.sdi.lab24.core"})
@Import({JPAConfig.class})
@PropertySources({@PropertySource(value = "classpath:local/db-dev.properties"),
})
public class AppLocalConfigDev implements AppLocalConfigInterface {
}

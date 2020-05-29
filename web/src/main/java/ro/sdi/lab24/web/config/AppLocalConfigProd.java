package ro.sdi.lab24.web.config;

import org.springframework.context.annotation.*;
import ro.sdi.lab24.core.config.JPAConfig;

@Configuration
@Profile("prod")
@ComponentScan({"ro.sdi.lab24.core"})
@Import({JPAConfig.class})
@PropertySources({@PropertySource(value = "classpath:local/db-prod.properties"),
})
public class AppLocalConfigProd implements AppLocalConfigInterface {
}

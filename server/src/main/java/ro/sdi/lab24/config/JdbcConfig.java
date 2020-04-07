package ro.sdi.lab24.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import ro.sdi.lab24.exception.DatabaseException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

@Configuration
public class JdbcConfig
{
    @Bean
    JdbcOperations jdbcOperations(DataSource dataSource)
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    DataSource dataSource()
    {
        BasicDataSource basicDataSource = new BasicDataSource();

        String username;
        String password;
        try (InputStream inputStream = new FileInputStream("database.properties"))
        {
            Properties properties = new Properties();
            properties.load(inputStream);
            username = Optional
                    .ofNullable(properties.getProperty("username"))
                    .orElseThrow(() -> new DatabaseException(
                            "No username specified in the database.properties file"
                    ));
            password = Optional
                    .ofNullable(properties.getProperty("password"))
                    .orElseThrow(() -> new DatabaseException(
                            "No password specified in the database.properties file"
                    ));
        }
        catch (IOException ignored)
        {
            throw new DatabaseException("Error reading database.properties file");
        }

        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/MovieRentalDB");
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setInitialSize(2);

        return basicDataSource;
    }
}

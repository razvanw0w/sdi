package ro.sdi.lab24.core.database;

import ro.sdi.lab24.core.exception.DatabaseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

/**
 * Utility class for connecting to a local PostgreSQL database <br />
 * Create a <b>database.properties</b> file in the root directory <br />
 * Fill it with the properties <b>username</b> and <b>password</b> and their according values.
 */
public class PostgreSQL
{
    private static final String connectionString = "jdbc:postgresql://localhost:5432/MovieRentalDB";

    private PostgreSQL()
    {
    }

    public static Connection newConnection()
    {
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

        try
        {
            return DriverManager.getConnection(connectionString, username, password);
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't connect to the PostgreSQL database");
        }
    }
}

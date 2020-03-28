package ro.sdi.lab24.networking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.model.Sort;
import ro.sdi.lab24.model.dto.ClientGenre;
import ro.sdi.lab24.model.dto.RentedMovieStatistic;
import ro.sdi.lab24.model.serialization.csv.ClientCSVSerializer;
import ro.sdi.lab24.model.serialization.csv.ClientGenreCSVSerializer;
import ro.sdi.lab24.model.serialization.csv.MovieCSVSerializer;
import ro.sdi.lab24.model.serialization.csv.RentalCSVSerializer;
import ro.sdi.lab24.model.serialization.csv.RentedMovieStatisticCSVSerializer;
import ro.sdi.lab24.model.serialization.csv.SortCSVSerializer;
import ro.sdi.lab24.serialization.CSVSerializer;
import ro.sdi.lab24.serialization.NetworkTranslator;

public class NetworkingUtils
{
    public static final int PORT = 1234;
    private static Map<Class<?>, CSVSerializer<?>> typeSerializers = new HashMap<>();

    static
    {
        typeSerializers.put(Client.class, new ClientCSVSerializer());
        typeSerializers.put(Movie.class, new MovieCSVSerializer());
        typeSerializers.put(Rental.class, new RentalCSVSerializer());
        typeSerializers.put(Sort.class, new SortCSVSerializer());
        typeSerializers.put(ClientGenre.class, new ClientGenreCSVSerializer());
        typeSerializers.put(RentedMovieStatistic.class, new RentedMovieStatisticCSVSerializer());
    }

    private NetworkingUtils()
    {
    }

    public static Message exception(String exceptionMessage)
    {
        return new Message("exception", exceptionMessage);
    }

    public static Message success(List<String> value)
    {
        Message message = new Message("success");
        if (value != null)
            value.forEach(message::addString);
        return message;
    }

    public static Object deserializeByType(String string, Class<?> clazz)
    {
        if (clazz.equals(Integer.class)) return Integer.parseInt(string);
        if (clazz.equals(String.class)) return string;
        CSVSerializer<?> csvSerializer = typeSerializers.get(clazz);
        if (csvSerializer == null) return null;
        return NetworkTranslator.from(csvSerializer).decode(string);
    }

    public static String serialize(Object entity)
    {
        Class<?> clazz = entity.getClass();
        if (clazz.equals(Integer.class)) return entity.toString();
        if (clazz.equals(String.class)) return entity.toString();
        CSVSerializer csvSerializer = typeSerializers.get(clazz);
        if (csvSerializer == null) return null;
        return NetworkTranslator.from(csvSerializer).encode(entity);
    }

}

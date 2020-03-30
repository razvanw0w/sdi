package ro.sdi.lab24.networking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.sdi.lab24.exception.ProgramException;
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
import ro.sdi.lab24.serialization.NetworkSerializer;

public class NetworkingUtils
{
    private static Map<Class<?>, NetworkSerializer<?>> typeSerializers = new HashMap<>();

    static
    {
        typeSerializers.put(int.class, new IntegerSerializer());
        typeSerializers.put(Integer.class, new IntegerSerializer());
        typeSerializers.put(String.class, new StringSerializer());
        typeSerializers.put(Client.class, NetworkSerializer.from(new ClientCSVSerializer()));
        typeSerializers.put(Movie.class, NetworkSerializer.from(new MovieCSVSerializer()));
        typeSerializers.put(Rental.class, NetworkSerializer.from(new RentalCSVSerializer()));
        typeSerializers.put(Sort.class, NetworkSerializer.from(new SortCSVSerializer()));
        typeSerializers.put(
                ClientGenre.class,
                NetworkSerializer.from(new ClientGenreCSVSerializer())
        );
        typeSerializers.put(
                RentedMovieStatistic.class,
                NetworkSerializer.from(new RentedMovieStatisticCSVSerializer())
        );
    }

    private NetworkingUtils()
    {
    }

    public static Message exception(String exceptionMessage)
    {
        return new Message("exception", exceptionMessage);
    }

    public static void checkException(Message message) throws ProgramException
    {
        if (message.getHeader().equals("exception"))
        {
            List<String> messageBody = message.getBody();
            if (messageBody.size() != 1)
            {
                throw new RuntimeException("Received response was invalid");
            }
            throw new ProgramException(messageBody.get(0));
        }
    }

    public static boolean isSuccess(Message message)
    {
        return message.getHeader().equals("success");
    }

    public static Message success(List<String> value)
    {
        Message message = new Message("success");
        if (value != null)
            value.forEach(message::addString);
        return message;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserializeByType(String string, Class<T> clazz)
    {
        NetworkSerializer<?> serializer = typeSerializers.get(clazz);
        if (serializer == null) return null;
        return (T) serializer.decode(string);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String serialize(Object entity)
    {
        Class<?> clazz = entity.getClass();
        NetworkSerializer serializer = typeSerializers.get(clazz);
        if (serializer == null) return null;
        return serializer.encode(entity);
    }

}

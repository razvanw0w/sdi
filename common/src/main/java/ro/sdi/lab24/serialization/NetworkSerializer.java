package ro.sdi.lab24.serialization;

public interface NetworkSerializer<T>
{
    static <T> NetworkSerializer<T> from(CSVSerializer<T> serializer)
    {
        return new NetworkSerializer<T>()
        {
            @Override
            public String encode(T entity)
            {
                return serializer.serialize(entity);
            }

            @Override
            public T decode(String string)
            {
                return serializer.deserialize(string);
            }
        };
    }

    String encode(T entity);

    T decode(String string);
}

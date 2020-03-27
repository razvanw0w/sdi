package ro.sdi.lab24.serialization;

public interface NetworkTranslator<T>
{
    static <T> NetworkTranslator<T> from(CSVSerializer<T> serializer)
    {
        return new NetworkTranslator<T>()
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

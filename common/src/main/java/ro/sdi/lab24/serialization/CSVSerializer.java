package ro.sdi.lab24.serialization;

public interface CSVSerializer<T>
{
    String serialize(T entity);

    T deserialize(String string);
}

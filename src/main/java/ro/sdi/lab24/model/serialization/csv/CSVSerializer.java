package ro.sdi.lab24.model.serialization.csv;

public interface CSVSerializer<T>
{
    String serialize(T entity);

    T deserialize(String string);
}

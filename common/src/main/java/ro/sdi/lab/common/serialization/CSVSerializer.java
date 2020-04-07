package ro.sdi.lab.common.serialization;

public interface CSVSerializer<T>
{
    String serialize(T entity);

    T deserialize(String string);
}

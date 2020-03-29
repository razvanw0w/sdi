package ro.sdi.lab24.networking;

import ro.sdi.lab24.serialization.NetworkSerializer;

public class StringSerializer implements NetworkSerializer<String>
{
    @Override
    public String encode(String entity)
    {
        return entity;
    }

    @Override
    public String decode(String string)
    {
        return string;
    }
}

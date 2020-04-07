package ro.sdi.lab.common.networking;

import ro.sdi.lab.common.serialization.NetworkSerializer;

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

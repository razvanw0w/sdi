package ro.sdi.lab24.networking;

import ro.sdi.lab24.serialization.NetworkSerializer;

public class IntegerSerializer implements NetworkSerializer<Integer>
{
    @Override
    public String encode(Integer integer)
    {
        return String.valueOf(integer);
    }

    @Override
    public Integer decode(String string)
    {
        return Integer.parseInt(string);
    }
}

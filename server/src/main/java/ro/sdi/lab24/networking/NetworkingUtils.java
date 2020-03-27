package ro.sdi.lab24.networking;

public class NetworkingUtils
{
    public static final int PORT = 1234;

    private NetworkingUtils()
    {
    }

    public static Message exception(String exceptionMessage)
    {
        return new Message("exception", exceptionMessage);
    }
}

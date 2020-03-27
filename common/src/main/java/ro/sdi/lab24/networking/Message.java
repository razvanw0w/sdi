package ro.sdi.lab24.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Message
{
    /**
     * For the call
     * header = "controllername:methodname" | ex: "ClientController:findAll"
     * body = method parameters serialized as csv
     * <p>
     * For the response
     * header = either "succes" or "exception"
     * body = either the csv serialized entities or the exception message
     */
    private String header;
    private List<String> body = new LinkedList<>();

    public Message(String header)
    {
        this.header = header;
    }

    public Message(String header, String... body)
    {
        this.header = header;
        this.body = Arrays.asList(body);
    }

    public static void write(Message message, OutputStream outputStream) throws IOException
    {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(message.header + System.lineSeparator());
        dataOutputStream.writeInt(message.body.size());
        for (String string : message.body)
        {
            dataOutputStream.writeUTF(string + System.lineSeparator());
        }
    }

    public static Message read(InputStream inputStream) throws IOException
    {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String header = dataInputStream.readUTF();
        Message message = new Message(header);
        for (int i = 0; i < dataInputStream.readInt(); i++)
        {
            message.addString(dataInputStream.readUTF());
        }
        return message;
    }

    public void addString(String string)
    {
        body.add(string);
    }

    public String getHeader()
    {
        return header;
    }

    public List<String> getBody()
    {
        return body;
    }

    public void setBody(List<String> body)
    {
        this.body = body;
    }
}

package ro.sdi.lab24.networking;

import ro.sdi.lab24.exception.ConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    public static Message sendAndReceive(Message request) {
        try (Socket socket = new Socket(ServerInformation.HOST, ServerInformation.PORT);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()
        ) {
            Message.write(request, outputStream);
            Message response = Message.read(inputStream);
            return response;
        } catch (IOException e) {
            throw new ConnectionException("connection to server failed");
        }
    }
}

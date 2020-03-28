package ro.sdi.lab24.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

import ro.sdi.lab24.controller.ClientController;
import ro.sdi.lab24.controller.Controller;
import ro.sdi.lab24.controller.MovieController;
import ro.sdi.lab24.controller.RentalController;
import ro.sdi.lab24.networking.Message;

class HandleClientTask implements Runnable
{

    private final Socket client;
    private final Controller controller;
    private final ClientController clientController;
    private final MovieController movieController;
    private final RentalController rentalController;

    public HandleClientTask(
            Socket client,
            Controller controller,
            ClientController clientController,
            MovieController movieController,
            RentalController rentalController
    )
    {

        this.client = client;
        this.controller = controller;
        this.clientController = clientController;
        this.movieController = movieController;
        this.rentalController = rentalController;
    }

    @Override
    public void run()
    {
        try
        {
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();
            Message message = Message.read(inputStream);
            Message response = ControllerAdapter.handleMessage(
                    message,
                    controller,
                    clientController,
                    movieController,
                    rentalController
            );
            Objects.requireNonNull(response, "Error computing the response");
            Message.write(response, outputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

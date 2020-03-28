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
import ro.sdi.lab24.networking.NetworkingUtils;

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
            String[] splitHeader = message.getHeader().split(":");
            Message response;
            switch (splitHeader[0])
            {
                case "Controller":
                    response = ControllerAdapter.handle(message, controller);
                    break;
                case "ClientController":
                    response = ClientControllerAdapter.handle(message, clientController);
                    break;
                case "MovieController":
                    response = MovieControllerAdapter.handle(message, movieController);
                    break;
                case "RentalController":
                    response = RentalControllerAdapter.handle(message, rentalController);
                    break;
                default:
                    response = NetworkingUtils.exception("Invalid controller name");
                    break;
            }
            Objects.requireNonNull(response, "Error computing the response");
            Message.write(response, outputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

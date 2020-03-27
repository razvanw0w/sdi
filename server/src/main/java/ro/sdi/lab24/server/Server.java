package ro.sdi.lab24.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import ro.sdi.lab24.controller.ClientControllerImpl;
import ro.sdi.lab24.controller.ControllerImpl;
import ro.sdi.lab24.controller.MovieControllerImpl;
import ro.sdi.lab24.controller.RentalController;
import ro.sdi.lab24.networking.NetworkingUtils;

public class Server
{

    private final ControllerImpl controller;
    private final ClientControllerImpl clientController;
    private final MovieControllerImpl movieController;
    private final RentalController rentalController;
    private ExecutorService executorService;
    private Boolean running = true;

    public Server(
            ControllerImpl controller,
            ClientControllerImpl clientController,
            MovieControllerImpl movieController,
            RentalController rentalController,
            ExecutorService executorService
    )
    {

        this.controller = controller;
        this.clientController = clientController;
        this.movieController = movieController;
        this.rentalController = rentalController;
        this.executorService = executorService;
    }

    public void run() throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(NetworkingUtils.PORT);
        while (running)
        {
            Socket socket = serverSocket.accept();
            executorService.submit(new HandleClientTask(
                    socket,
                    controller,
                    clientController,
                    movieController,
                    rentalController
            ));
        }
        executorService.shutdown();
    }
}

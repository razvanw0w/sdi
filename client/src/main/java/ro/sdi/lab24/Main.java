package ro.sdi.lab24;

import javax.xml.parsers.ParserConfigurationException;

import ro.sdi.lab24.controller.ClientController;
import ro.sdi.lab24.controller.Controller;
import ro.sdi.lab24.controller.MovieController;
import ro.sdi.lab24.controller.RentalController;
import ro.sdi.lab24.view.Console;

public class Main
{
    public static void main(String[] args) throws ParserConfigurationException
    {
        Controller controller = null;
        ClientController clientController = null;
        MovieController movieController = null;
        RentalController rentalController = null;

        Console.initialize(
                controller,
                clientController,
                movieController,
                rentalController
        );

        Console.run(args);
    }
}

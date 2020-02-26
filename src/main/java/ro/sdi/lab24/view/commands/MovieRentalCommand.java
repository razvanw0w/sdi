package ro.sdi.lab24.view.commands;

import ro.sdi.lab24.view.commands.client.ClientCommand;
import ro.sdi.lab24.view.commands.movie.MovieCommand;
import ro.sdi.lab24.view.commands.rental.RentalCommand;

import static picocli.CommandLine.Command;

@Command(name = "",
        subcommands = {
                ClientCommand.class,
                MovieCommand.class,
                RentalCommand.class
        },
        mixinStandardHelpOptions = true)
public class MovieRentalCommand implements Runnable
{
        @Override
        public void run()
        {
                System.out.println("Use a command!");
        }
}

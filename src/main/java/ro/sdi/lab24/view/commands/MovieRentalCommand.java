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
        })
public class MovieRentalCommand
{
}

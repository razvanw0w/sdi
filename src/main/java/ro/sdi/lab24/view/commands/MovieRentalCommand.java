package ro.sdi.lab24.view.commands;

import ro.sdi.lab24.view.commands.client.AddClientCommand;
import ro.sdi.lab24.view.commands.client.ClientCommand;
import ro.sdi.lab24.view.commands.client.DeleteClientCommand;
import ro.sdi.lab24.view.commands.client.ListClientsCommand;
import ro.sdi.lab24.view.commands.client.UpdateClientCommand;
import ro.sdi.lab24.view.commands.movie.AddMovieCommand;
import ro.sdi.lab24.view.commands.movie.DeleteMovieCommand;
import ro.sdi.lab24.view.commands.movie.ListMoviesCommand;
import ro.sdi.lab24.view.commands.movie.MovieCommand;
import ro.sdi.lab24.view.commands.movie.UpdateMovieCommand;
import ro.sdi.lab24.view.commands.rental.AddRentalCommand;
import ro.sdi.lab24.view.commands.rental.DeleteRentalCommand;
import ro.sdi.lab24.view.commands.rental.ListRentalsCommand;
import ro.sdi.lab24.view.commands.rental.UpdateRentalCommand;

import static picocli.CommandLine.Command;

@Command(name = "",
        subcommands = {
                ClientCommand.class,
                MovieCommand.class,
                ClientCommand.class
        })
public class MovieRentalCommand
{
}

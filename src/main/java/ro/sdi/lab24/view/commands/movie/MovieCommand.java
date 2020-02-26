package ro.sdi.lab24.view.commands.movie;

import picocli.CommandLine.Command;

@Command(name = "movie", description = "Manage movies", subcommands = {
        AddMovieCommand.class,
        ListMoviesCommand.class,
        UpdateMovieCommand.class,
        DeleteMovieCommand.class,
})
public class MovieCommand
{
}

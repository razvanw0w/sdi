package ro.sdi.lab24.view.commands.movie;

import picocli.CommandLine;

@CommandLine.Command(description = "Manage movies", subcommands = {
        AddMovieCommand.class,
        ListMoviesCommand.class,
        UpdateMovieCommand.class,
        DeleteMovieCommand.class,
})
public class MovieCommand
{
}

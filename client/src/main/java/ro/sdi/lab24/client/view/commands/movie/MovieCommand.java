package ro.sdi.lab24.client.view.commands.movie;

import picocli.CommandLine.Command;

@Command(name = "movie", description = "Manage movies", subcommands = {
        AddMovieCommand.class,
        ListMoviesCommand.class,
        UpdateMovieCommand.class,
        DeleteMovieCommand.class,
        FilterMoviesCommand.class,
        SortMoviesCommand.class
},mixinStandardHelpOptions = true)
public class MovieCommand implements Runnable
{
    @Override
    public void run()
    {
        System.out.println("Use a subcommand! Type 'movie --help'");
    }
}

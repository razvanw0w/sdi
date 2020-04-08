package ro.sdi.lab.client.view.commands.movie;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.client.view.FutureResponse;
import ro.sdi.lab.client.view.ResponseMapper;

@Command(description = "Delete a movie", name = "delete")
public class DeleteMovieCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int id;

    @Override
    public void run() {
        int id = this.id;
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.movieController.deleteMovie(id),
                        new ResponseMapper<>(response -> String.format("Movie %d deleted!", id))
                )
        );
    }
}

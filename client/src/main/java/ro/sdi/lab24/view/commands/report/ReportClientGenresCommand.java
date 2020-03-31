package ro.sdi.lab24.view.commands.report;

import picocli.CommandLine.Command;
import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.FutureResponse;
import ro.sdi.lab24.view.ResponseMapper;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Command(description = "Shows the most watched genre for each client", name = "clientgenres")
public class ReportClientGenresCommand implements Runnable
{
    @Override
    public void run() {
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.controller.getClientGenres(),
                        new ResponseMapper<>(response -> {
                            if (!response.iterator().hasNext()) {
                                return "No client - favourite genre data found!";
                            }
                            return "Most watched genre for each client\n" +
                                    StreamSupport.stream(response.spliterator(), false)
                                            .map(clientGenre -> String.format("%s - %s", clientGenre.getClient(), clientGenre.getGenre()))
                                            .collect(Collectors.joining("\n", "", "\n"));
                        })
                )
        );
    }
}
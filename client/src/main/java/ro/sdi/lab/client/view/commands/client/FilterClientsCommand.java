package ro.sdi.lab.client.view.commands.client;

import picocli.CommandLine;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.client.view.FutureResponse;
import ro.sdi.lab.client.view.ResponseMapper;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CommandLine.Command(description = "Filter clients by name", name = "filter")
public class FilterClientsCommand implements Runnable
{
    @CommandLine.Parameters(index = "0", description = "Client name")
    String name;

    @Override
    public void run() {
        String name = this.name;
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.clientController.filterClientsByName(name),
                        new ResponseMapper<>(response -> {
                            if (!response.iterator().hasNext()) {
                                return "No clients found!";
                            }
                            return String.format("Filtered clients by name = %s\n", name) +
                                    StreamSupport.stream(response.spliterator(), false)
                                            .map(client -> String.format("%d %s", client.getId(), client.getName()))
                                            .collect(Collectors.joining("\n", "", "\n"));
                        })
                )
        );
    }
}

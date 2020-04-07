package ro.sdi.lab.client.view.commands.client;

import picocli.CommandLine.Command;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.client.view.FutureResponse;
import ro.sdi.lab.client.view.ResponseMapper;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Command(description = "List all clients", name = "list")
public class ListClientsCommand implements Runnable
{
    @Override
    public void run() {
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.clientController.getClients(),
                        new ResponseMapper<>(response -> {
                            if (!response.iterator().hasNext()) {
                                return "No clients found!";
                            }
                            return "List of clients\n" +
                                    StreamSupport.stream(response.spliterator(), false)
                                            .map(client -> String.format("%d %s", client.getId(), client.getName()))
                                            .collect(Collectors.joining("\n", "", "\n"));
                        })
                )
        );
    }
}

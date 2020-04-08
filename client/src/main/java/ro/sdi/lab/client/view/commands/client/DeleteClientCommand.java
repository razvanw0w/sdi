package ro.sdi.lab.client.view.commands.client;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.client.view.FutureResponse;
import ro.sdi.lab.client.view.ResponseMapper;

@Command(description = "Delete a client", name = "delete")
public class DeleteClientCommand implements Runnable
{
    @Parameters(index = "0", description = "Client id")
    int id;

    @Override
    public void run() {
        int id = this.id;
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.clientController.deleteClient(id),
                        new ResponseMapper<>(response -> String.format("Client %d deleted!", id))
                )
        );
    }
}

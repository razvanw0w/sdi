package ro.sdi.lab24.view.commands.client;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.FutureResponse;
import ro.sdi.lab24.view.ResponseMapper;

@Command(description = "Delete a client", name = "delete")
public class DeleteClientCommand implements Runnable
{
    @Parameters(index = "0", description = "Client id")
    int id;

    @Override
    public void run() {
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.clientController.deleteClient(id),
                        new ResponseMapper<>(response -> "Client deleted!")
                )
        );
    }
}

package ro.sdi.lab24.view.commands.client;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.FutureResponse;
import ro.sdi.lab24.view.ResponseMapper;

@Command(description = "Add a client", name = "add")
public class AddClientCommand implements Runnable
{
    @Parameters(index = "0", description = "Client id")
    int id;

    @Parameters(index = "1", description = "Client name")
    String name;

    @Override
    public void run()
    {
        int id = this.id;
        String name = this.name;
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.clientController.addClient(id, name),
                        new ResponseMapper<>(response -> String.format("Client %d,%s added!", id, name))
                )
        );
    }
}

package ro.sdi.lab24.view.commands.client;

import ro.sdi.lab24.view.Console;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

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
        Console.clientController.addClient(id, name);
    }
}

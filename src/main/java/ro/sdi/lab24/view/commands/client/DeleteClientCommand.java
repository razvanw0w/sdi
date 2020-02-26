package ro.sdi.lab24.view.commands.client;

import picocli.CommandLine.Parameters;

import picocli.CommandLine.Command;
import ro.sdi.lab24.view.Console;

@Command(description = "Delete a client", name = "delete")
public class DeleteClientCommand implements Runnable
{
    @Parameters(index = "0", description = "Client id")
    int id;


    @Override
    public void run()
    {
        Console.clientController.deleteClient(id);
    }
}

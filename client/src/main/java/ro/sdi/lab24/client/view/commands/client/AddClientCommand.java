package ro.sdi.lab24.client.view.commands.client;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab24.client.view.Console;
import ro.sdi.lab24.core.exception.ProgramException;

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
        try
        {
            Console.clientController.addClient(id, name);
            System.out.println("Client added!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}

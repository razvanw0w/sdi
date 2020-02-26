package ro.sdi.lab24.view.commands.client;

import picocli.CommandLine.Command;
import ro.sdi.lab24.validation.ProgramException;
import ro.sdi.lab24.view.Console;

@Command(description = "List all clients", name = "list")
public class ListClientsCommand implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            Console.clientController.getClients().forEach(
                    client -> System.out.printf("%d %s", client.getId(), client.getName())
            );
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}

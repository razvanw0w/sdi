package ro.sdi.lab24.client.view.commands.client;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab24.client.view.Console;
import ro.sdi.lab24.core.exception.ProgramException;

@Command(description = "Delete a client", name = "delete")
public class DeleteClientCommand implements Runnable
{
    @Parameters(index = "0", description = "Client id")
    int id;

    @Override
    public void run()
    {
        try
        {
            Console.clientController.deleteClient(id);
            System.out.println("Client deleted!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}

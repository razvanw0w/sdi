package ro.sdi.lab24.view.commands.client;

import picocli.CommandLine.Command;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.view.Console;

@Command(name = "client", description = "Manage clients", subcommands = {
        AddClientCommand.class,
        ListClientsCommand.class,
        UpdateClientCommand.class,
        DeleteClientCommand.class,
})
public class ClientCommand implements Runnable
{
    @Override
    public void run()
    {
            System.out.println("Use a subcommand!");
    }
}

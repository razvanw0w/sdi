package ro.sdi.lab24.view.commands.client;

import picocli.CommandLine.Command;

@Command(name = "client", description = "Manage clients", subcommands = {
        AddClientCommand.class,
        ListClientsCommand.class,
        UpdateClientCommand.class,
        DeleteClientCommand.class,
})
public class ClientCommand
{
}

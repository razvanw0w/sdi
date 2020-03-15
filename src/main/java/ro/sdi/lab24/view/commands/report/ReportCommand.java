package ro.sdi.lab24.view.commands.report;

import picocli.CommandLine.Command;

@Command(name = "report", description = "Manage reports", subcommands = {
        ReportTopMoviesCommand.class
}, mixinStandardHelpOptions = true)
public class ReportCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("Use a subcommand!  Type 'report --help'");
    }
}

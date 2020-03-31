package ro.sdi.lab24.view.commands.results;

import picocli.CommandLine;
import ro.sdi.lab24.view.Console;

@CommandLine.Command(description = "Displays all results until now", name = "results")
public class ResultsCommand implements Runnable
{
    @Override
    public void run()
    {
        System.out.println(String.join("\n", Console.responseBuffer.getResponses()));
    }
}

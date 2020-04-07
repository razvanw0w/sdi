package ro.sdi.lab.client.view.commands.results;

import picocli.CommandLine;
import ro.sdi.lab.client.view.Console;

@CommandLine.Command(description = "Displays all results until now", name = "results")
public class ResultsCommand implements Runnable
{
    @Override
    public void run()
    {
        System.out.println(String.join("\n", Console.responseBuffer.getResponses()));
    }
}
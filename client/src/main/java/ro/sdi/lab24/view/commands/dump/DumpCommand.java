package ro.sdi.lab24.view.commands.dump;

import picocli.CommandLine;
import ro.sdi.lab24.view.Console;

@CommandLine.Command(description = "Dump all results until now", name = "dump")
public class DumpCommand implements Runnable {
    @Override
    public void run() {
        System.out.println(String.join("\n", Console.responseBuffer.getResponses()));
    }
}

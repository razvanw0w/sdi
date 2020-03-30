package ro.sdi.lab24.view.commands.dump;

import picocli.CommandLine;
import ro.sdi.lab24.view.Console;

import java.util.stream.Collectors;

@CommandLine.Command(description = "Dump all results until now", name = "dump")
public class DumpCommand implements Runnable {
    @Override
    public void run() {
        System.out.println(Console.responseBuffer.getResponses()
                .stream()
                .collect(Collectors.joining("\n", "", "\n"))
        );
    }
}

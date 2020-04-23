package ro.sdi.lab24.client.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import ro.sdi.lab24.client.controller.ClientController;
import ro.sdi.lab24.client.controller.Controller;
import ro.sdi.lab24.client.controller.MovieController;
import ro.sdi.lab24.client.controller.RentalController;
import ro.sdi.lab24.client.view.commands.MovieRentalCommand;
import ro.sdi.lab24.core.exception.ProgramException;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Console {
    @Autowired
    private Controller autowiredController;
    @Autowired
    private ClientController autowiredClientController;
    @Autowired
    private MovieController autowiredMovieController;
    @Autowired
    private RentalController autowiredRentalController;

    public static Controller controller;
    public static ClientController clientController;
    public static MovieController movieController;
    public static RentalController rentalController;

    @PostConstruct
    private void initialize() {
        Console.controller = autowiredController;
        Console.clientController = autowiredClientController;
        Console.movieController = autowiredMovieController;
        Console.rentalController = autowiredRentalController;
    }

    public static void handleException(ProgramException e) {
        System.out.println(e.getMessage());
    }

    public static void run(String[] args) {
        System.out.println("Movie rental software");
        CommandLine commandLine = new CommandLine(MovieRentalCommand.class);
        commandLine.setUnmatchedOptionsArePositionalParams(true);
        commandLine.setErr(new PrintWriter(System.out));
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("> ");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("exit")) {
                    break;
                } else if (!line.matches("^(movie|rental|client|report|--help|-h).*")) {
                    System.out.println("Invalid command! Type '--help'");
                } else {
                    commandLine.execute(parseLine(line));
                }
                System.out.print("> ");
            }
            return;
        }

        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    protected static String[] parseLine(String line) {
        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher regexMatcher = regex.matcher(line);
        while (regexMatcher.find()) {
            if (regexMatcher.group(1) != null) {
                // Add double-quoted string without the quotes
                matchList.add(regexMatcher.group(1));
            } else if (regexMatcher.group(2) != null) {
                // Add single-quoted string without the quotes
                matchList.add(regexMatcher.group(2));
            } else {
                // Add unquoted word
                matchList.add(regexMatcher.group());
            }
        }
        return matchList.toArray(new String[0]);
    }
}

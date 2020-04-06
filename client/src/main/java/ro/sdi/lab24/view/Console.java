package ro.sdi.lab24.view;

import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;
import ro.sdi.lab24.ResponseDaemon;
import ro.sdi.lab24.controller.FutureClientController;
import ro.sdi.lab24.controller.FutureController;
import ro.sdi.lab24.controller.FutureMovieController;
import ro.sdi.lab24.controller.FutureRentalController;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.view.commands.MovieRentalCommand;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console {
    public static FutureController controller;
    public static FutureClientController clientController;
    public static FutureMovieController movieController;
    public static FutureRentalController rentalController;
    public static DateTimeFormatter dateformatter;
    public static ResponseBuffer responseBuffer;
    public static Timer timer;
    @Autowired
    private FutureController autowiredController;
    @Autowired
    private FutureClientController autowiredClientController;
    @Autowired
    private FutureMovieController autowiredMovieController;
    @Autowired
    private FutureRentalController autowiredRentalController;
    @Autowired
    private DateTimeFormatter autowiredDateFormatter;
    @Autowired
    private ResponseBuffer autowiredResponseBuffer;
    @Autowired
    private Timer autowiredTimer;

    @PostConstruct
    private void initialize() {
        Console.controller = autowiredController;
        Console.clientController = autowiredClientController;
        Console.movieController = autowiredMovieController;
        Console.rentalController = autowiredRentalController;
        Console.dateformatter = autowiredDateFormatter;
        Console.responseBuffer = autowiredResponseBuffer;
        Console.timer = autowiredTimer;
        timer.scheduleAtFixedRate(new ResponseDaemon(responseBuffer), 10 * 1000, 10 * 1000);
    }

    public static String handleException(ProgramException e)
    {
        return e.getMessage();
    }

    public static void run(String[] args)
    {
        System.out.println("Movie rental software");
        CommandLine commandLine = new CommandLine(MovieRentalCommand.class);
        commandLine.setUnmatchedOptionsArePositionalParams(true);
        commandLine.setErr(new PrintWriter(System.out));
        if (args.length == 0)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print("> ");
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (line.equals("exit"))
                {
                    break;
                }
                else if (!line.matches("^(movie|rental|client|report|results|--help|-h).*"))
                {
                    System.out.println("Invalid command! Type '--help'");
                }
                else
                {
                    commandLine.execute(parseLine(line));
                }
                System.out.print("> ");
            }
            return;
        }

        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    protected static String[] parseLine(String line)
    {
        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher regexMatcher = regex.matcher(line);
        while (regexMatcher.find())
        {
            if (regexMatcher.group(1) != null)
            {
                // Add double-quoted string without the quotes
                matchList.add(regexMatcher.group(1));
            } else if (regexMatcher.group(2) != null)
            {
                // Add single-quoted string without the quotes
                matchList.add(regexMatcher.group(2));
            } else
            {
                // Add unquoted word
                matchList.add(regexMatcher.group());
            }
        }
        return matchList.toArray(new String[0]);
    }
}

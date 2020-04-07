package ro.sdi.lab.client.view.commands.movie;

import picocli.CommandLine;
import ro.sdi.lab.common.exception.SortingException;
import ro.sdi.lab.common.model.Sort;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.client.view.FutureResponse;
import ro.sdi.lab.client.view.ResponseMapper;
import ro.sdi.lab.client.view.commands.movie.utils.SortingCriteria;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CommandLine.Command(description = "sort movies", name = "sort")
public class SortMoviesCommand implements Runnable {
    @CommandLine.Parameters(arity = "2..6", description = "one to three sorting criteria")
    String[] criteriaStrings;

    private SortingCriteria[] convertStringsToCriteria() {
        if (criteriaStrings.length % 2 == 1)
            throw new SortingException("invalid sorting options");
        SortingCriteria[] criteria = new SortingCriteria[criteriaStrings.length / 2];
        for (int i = 0; i < criteriaStrings.length; i += 2) {
            criteria[i / 2] = new SortingCriteria(criteriaStrings[i], criteriaStrings[i + 1]);
        }
        return criteria;
    }

    @Override
    public void run() {
        SortingCriteria[] criteria = convertStringsToCriteria();
        Sort reducedSort = Arrays.stream(criteria, 0, criteria.length)
                .map(sort -> new Sort(sort.getDirection(), sort.getField()))
                .reduce(Sort::and)
                .orElseThrow(() -> new SortingException("no sorting criteria provided"));
        String header = getSortingHeader();
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.movieController.sortMovies(reducedSort),
                        new ResponseMapper<>(response -> {
                            if (!response.iterator().hasNext()) {
                                return "No movies found!";
                            }
                            return header + "\n" +
                                    StreamSupport.stream(response.spliterator(), false)
                                            .map(movie -> String.format("%d %s %s %d", movie.getId(), movie.getName(), movie.getGenre(), movie.getRating()))
                                            .collect(Collectors.joining("\n", "", "\n"));
                        })
                )
        );
    }

    private String getSortingHeader() {
        StringBuilder header = new StringBuilder("Movies sorted by ");
        for (int i = 0; i < criteriaStrings.length; i += 2) {
            if (i > 0)
                header.append(" ");
            header.append(criteriaStrings[i]).append(" ").append(criteriaStrings[i + 1]);
        }
        return header.toString();
    }
}

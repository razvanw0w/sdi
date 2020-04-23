package ro.sdi.lab24.core.sorting;

import ro.sdi.lab24.core.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sort
{
    List<Pair<Direction, String>> sortingFields = new ArrayList<>();

    public Sort(String field)
    {
        addSorting(Direction.ASC, field);
    }

    public Sort(Direction direction, String... fields)
    {
        addSorting(direction, fields);
    }

    public Sort and(Sort sort)
    {
        sort.sortingFields.forEach(pair ->
                                   {
                                       addSorting(pair.getKey(), pair.getValue());
                                   });
        return this;
    }

    private void addSorting(Direction direction, String... fields)
    {
        sortingFields.addAll(
                Arrays.stream(fields)
                      .map(field -> new Pair<>(direction, field))
                      .collect(Collectors.toList())
        );
    }

    public enum Direction
    {
        ASC,
        DESC
    }
}

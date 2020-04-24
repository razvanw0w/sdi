package ro.sdi.lab24.core.sorting;

import ro.sdi.lab24.core.utils.SortUnit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sort implements Serializable {
    List<SortUnit> sortingFields = new ArrayList<>();

    public Sort(String field) {
        addSorting(Direction.ASC, field);
    }

    public Sort(Direction direction, String... fields) {
        addSorting(direction, fields);
    }

    public Sort(Sort.Direction direction, String field) {
        addSorting(direction, field);
    }

    public Sort() {

    }

    public List<SortUnit> getSortingFields() {
        return sortingFields;
    }

    public Sort and(Sort sort) {
        sort.sortingFields.forEach(pair ->
        {
            addSorting(pair.getDirection(), pair.getField());
        });
        return this;
    }

    private void addSorting(Direction direction, String... fields)
    {
        sortingFields.addAll(
                Arrays.stream(fields)
                        .map(field -> new SortUnit(direction, field))
                      .collect(Collectors.toList())
        );
    }

    public enum Direction
    {
        ASC,
        DESC
    }
}

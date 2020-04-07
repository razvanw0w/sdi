package ro.sdi.lab.common.model.serialization.csv;

import ro.sdi.lab.common.exception.ParsingException;
import ro.sdi.lab.common.model.Sort;
import ro.sdi.lab.common.serialization.CSVSerializer;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SortCSVSerializer implements CSVSerializer<Sort>
{
    @Override
    public String serialize(Sort entity) {
        return entity.sortingFields.stream()
                .map(field -> String.format("%s:%s", field.getKey().equals(Sort.Direction.ASC) ? "asc" : "desc",
                        field.getValue()))
                .collect(Collectors.joining(","));
    }

    private Sort fieldsToSort(String[] fields) {
        return new Sort(fields[0].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, fields[1]);
    }

    @Override
    public Sort deserialize(String string) {
        return Arrays.stream(string.split(","))
                .map(field -> fieldsToSort(field.split(":")))
                .reduce(Sort::and)
                .orElseThrow(() -> new ParsingException("Sort csv string cannot be parsed"));
    }
}

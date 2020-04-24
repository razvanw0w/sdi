package ro.sdi.lab24.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.sorting.Sort;
import ro.sdi.lab24.web.dto.SortDTO;

import java.util.stream.Collectors;

@Component
public class SortConverter implements Converter<Sort, SortDTO> {
    private final SortUnitConverter sortUnitConverter;

    @Autowired
    public SortConverter(SortUnitConverter sortUnitConverter) {
        this.sortUnitConverter = sortUnitConverter;
    }

    @Override
    public Sort toModel(SortDTO dto) {
        Sort sort = dto.getUnits()
                .stream()
                .map(sortUnitConverter::toModel)
                .map(unit -> new Sort(unit.getDirection(), unit.getField()))
                .reduce(Sort::and)
                .orElse(new Sort());
        return sort;
    }

    @Override
    public SortDTO toDTO(Sort sort) {
        return new SortDTO(
                sort.getSortingFields()
                        .stream()
                        .map(sortUnitConverter::toDTO)
                        .collect(Collectors.toSet())
        );
    }
}

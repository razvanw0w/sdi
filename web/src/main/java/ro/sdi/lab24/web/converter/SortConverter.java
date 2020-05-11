package ro.sdi.lab24.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.utils.SortUnit;
import ro.sdi.lab24.web.dto.SortDTO;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class SortConverter implements Converter<Sort, SortDTO> {
    private final SortUnitConverter sortUnitConverter;

    @Autowired
    public SortConverter(SortUnitConverter sortUnitConverter) {
        this.sortUnitConverter = sortUnitConverter;
    }

    @Override
    public Sort toModel(SortDTO dto) {
        return dto.getUnits()
                .stream()
                .map(sortUnitConverter::toModel)
                .map(unit -> new Sort(unit.getDirection(), unit.getField()))
                .reduce(Sort::and)
                .orElse(Sort.unsorted());
    }

    @Override
    public SortDTO toDTO(Sort sort) {
        return new SortDTO(
                StreamSupport.stream(sort.spliterator(), false)
                        .map(order -> new SortUnit(order.getDirection(), order.getProperty()))
                        .map(sortUnitConverter::toDTO)
                        .collect(Collectors.toList())
        );
    }
}

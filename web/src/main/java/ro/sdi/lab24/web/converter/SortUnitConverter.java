package ro.sdi.lab24.web.converter;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.utils.SortUnit;
import ro.sdi.lab24.web.dto.SortUnitDTO;

@Component
public class SortUnitConverter implements Converter<SortUnit, SortUnitDTO> {
    @Override
    public SortUnit toModel(SortUnitDTO sortUnitDTO) {
        Sort.Direction direction = sortUnitDTO.getDirection().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return new SortUnit(direction, sortUnitDTO.getField());
    }

    @Override
    public SortUnitDTO toDTO(SortUnit sortUnit) {
        return SortUnitDTO.builder()
                .direction(sortUnit.getDirection() == Sort.Direction.ASC ? "asc" : "desc")
                .field(sortUnit.getField())
                .build();
    }
}

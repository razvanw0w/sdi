package ro.sdi.lab24.web.converter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface Converter<Model, DTO> {
    Model toModel(DTO dto);

    DTO toDTO(Model model);

    default List<DTO> toDTOList(Iterable<Model> models) {
        return StreamSupport.stream(models.spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

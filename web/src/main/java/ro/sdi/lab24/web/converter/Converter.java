package ro.sdi.lab24.web.converter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface Converter<Model, DTO> {
    Model toModel(DTO dto);

    DTO toDTO(Model model);

    default Set<DTO> toDTOSet(Iterable<Model> models) {
        return StreamSupport.stream(models.spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }
}

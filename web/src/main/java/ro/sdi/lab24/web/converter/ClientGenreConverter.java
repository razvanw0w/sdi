package ro.sdi.lab24.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.service.dto.ClientGenre;
import ro.sdi.lab24.web.dto.ClientGenreDTO;

@Component
public class ClientGenreConverter implements Converter<ClientGenre, ClientGenreDTO> {
    private final ClientConverter clientConverter;

    @Autowired
    public ClientGenreConverter(ClientConverter clientConverter) {
        this.clientConverter = clientConverter;
    }

    @Override
    public ClientGenre toModel(ClientGenreDTO clientGenreDTO) {
        return new ClientGenre(
                clientConverter.toModel(clientGenreDTO.getClient()),
                clientGenreDTO.getGenre()
        );
    }

    @Override
    public ClientGenreDTO toDTO(ClientGenre clientGenre) {
        return ClientGenreDTO.builder()
                .client(
                        clientConverter.toDTO(clientGenre.getClient())
                )
                .genre(clientGenre.getGenre())
                .build();
    }
}

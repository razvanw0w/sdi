package ro.sdi.lab24.web.converter;

import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.web.dto.ClientDTO;

@Component
public class ClientConverter implements Converter<Client, ClientDTO> {
    @Override
    public Client toModel(ClientDTO clientDTO) {
        return new Client(clientDTO.getId(), clientDTO.getName());
    }

    @Override
    public ClientDTO toDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .build();
    }
}

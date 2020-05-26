package ro.sdi.lab24.web.converter;

import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.web.dto.ClientDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ClientConverter implements Converter<Client, ClientDTO> {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Client toModel(ClientDTO clientDTO) {
        Client client = Client.builder()
                .name(clientDTO.getName())
                .fidelity(clientDTO.getFidelity())
                .build();
        client.setId(clientDTO.getId());
        return client;
    }

    @Override
    public ClientDTO toDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .fidelity(client.getFidelity())
                .build();
    }
}

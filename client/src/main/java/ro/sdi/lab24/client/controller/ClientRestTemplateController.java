package ro.sdi.lab24.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.sdi.lab24.core.exception.AlreadyExistingElementException;
import ro.sdi.lab24.core.exception.ElementNotFoundException;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.web.converter.ClientConverter;
import ro.sdi.lab24.web.dto.ClientsDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientRestTemplateController {
    public static final String url = "http://localhost:8080/api/clients";
    private static final Logger log = LoggerFactory.getLogger(ClientRestTemplateController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ClientConverter clientConverter;

    public void addClient(int id, String name) {
        Client client = new Client(id, name);
        try {
            restTemplate.postForEntity(url, client, Object.class);
            log.trace("client {} added", client);
        } catch (RestClientException e) {
            e.printStackTrace();
            log.trace("client {} already exists", client);
            throw new AlreadyExistingElementException("Client already exists!");
        }
    }

    public void deleteClient(int id) {
        try {
            restTemplate.delete(url + "/" + id);
            log.trace("client id = {} deleted", id);
        } catch (RestClientException e) {
            log.trace("client id = {} was not deleted", id);
            throw new ElementNotFoundException("Client does not exist!");
        }
    }

    public Iterable<Client> getClients() {
        ClientsDTO dto = restTemplate.getForObject(url, ClientsDTO.class);
        assert dto != null;
        List<Client> clients = dto.getClients()
                .stream()
                .map(clientConverter::toModel)
                .collect(Collectors.toUnmodifiableList());
        log.trace("fetched clients: {}", clients);
        return clients;
    }

    public void updateClient(int id, String name) {
        Client client = new Client(id, name);
        try {
            restTemplate.put(url + "/" + id, client);
            log.trace("client id = {} updated: {}", id, client);
        } catch (RestClientException e) {
            log.trace("client id = {} was not updated", id);
            throw new ElementNotFoundException("Client not found!");
        }
    }

    public Iterable<Client> filterClientsByName(String name) {
        ClientsDTO dto = restTemplate.getForObject(url + "/filter/" + name, ClientsDTO.class);
        assert dto != null;
        List<Client> clients = dto.getClients()
                .stream()
                .map(clientConverter::toModel)
                .collect(Collectors.toUnmodifiableList());
        log.trace("filtered clients by name = {}: {}", name, clients);
        return clients;
    }
}

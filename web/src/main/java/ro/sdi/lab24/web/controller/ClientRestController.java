package ro.sdi.lab24.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import ro.sdi.lab24.core.controller.ClientCoreController;
import ro.sdi.lab24.core.exception.AlreadyExistingElementException;
import ro.sdi.lab24.core.exception.ElementNotFoundException;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.web.converter.ClientConverter;
import ro.sdi.lab24.web.dto.ClientDTO;
import ro.sdi.lab24.web.dto.ClientsDTO;

@RestController
public class ClientRestController {
    public static final Logger log = LoggerFactory.getLogger(ClientRestController.class);

    @Autowired
    private ClientCoreController clientCoreController;

    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public ClientsDTO getClients() {
        Iterable<Client> clients = clientCoreController.getClients();
        log.trace("fetch clients: {}", clients);
        return new ClientsDTO(clientConverter.toDTOSet(clients));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public ResponseEntity<?> addClient(@RequestBody ClientDTO dto) {
        Client client = clientConverter.toModel(dto);
        try {
            clientCoreController.addClient(client.getId(), client.getName());
        } catch (AlreadyExistingElementException e) {
            log.trace("client id = {} already exists", client.getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("client {} added", client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteClient(@PathVariable int id) {
        try {
            clientCoreController.deleteClient(id);
        } catch (ElementNotFoundException e) {
            log.trace("client id = {} could not be deleted", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("client id = {} deleted", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateClient(@PathVariable int id, @RequestBody ClientDTO dto) {
        Client client = clientConverter.toModel(dto);
        try {
            clientCoreController.updateClient(id, client.getName());
        } catch (ElementNotFoundException e) {
            log.trace("client id = {} could not be updated", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("client id = {} updated: {}", id, client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/filter/{name}", method = RequestMethod.GET)
    public ClientsDTO filterClientsByName(@PathVariable String name) {
        log.trace("filtered clients by name = {}", name);
        return new ClientsDTO(
                clientConverter.toDTOSet(
                        clientCoreController.filterClientsByName(name)
                )
        );
    }
}

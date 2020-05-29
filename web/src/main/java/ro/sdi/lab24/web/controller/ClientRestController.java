package ro.sdi.lab24.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import ro.sdi.lab24.core.exception.AlreadyExistingElementException;
import ro.sdi.lab24.core.exception.ElementNotFoundException;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.core.service.ClientService;
import ro.sdi.lab24.web.converter.ClientConverter;
import ro.sdi.lab24.web.converter.RentalConverter;
import ro.sdi.lab24.web.dto.ClientDTO;
import ro.sdi.lab24.web.dto.ClientsDTO;

import java.time.format.DateTimeFormatter;

@RestController
public class ClientRestController {
    private static final Logger log = LoggerFactory.getLogger(ClientRestController.class);
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;

    @Autowired
    private RentalConverter rentalConverter;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public ClientsDTO getClients() {
        Iterable<Client> clients = clientService.getClients();
        log.trace("fetch clients: {}", clients);
        return new ClientsDTO(clientConverter.toDTOList(clients));
    }

    @RequestMapping(value = "/clients/page={page}&size={size}", method = RequestMethod.GET)
    public ClientsDTO getClientsPaginated(@PathVariable int page, @PathVariable int size) {
        Iterable<Client> clients = clientService.getClientsPaginated(page, size);
        log.trace("fetch clients paginated (size={} page={}): {}", size, page, clients);
        return new ClientsDTO(clientConverter.toDTOList(clients));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public ResponseEntity<?> addClient(@RequestBody ClientDTO dto) {
        Client client = clientConverter.toModel(dto);
        try {
            clientService.addClient(client.getName(), client.getFidelity());
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
            clientService.deleteClient(id);
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
            clientService.updateClient(id, client.getName(), client.getFidelity());
        } catch (ElementNotFoundException e) {
            log.trace("client id = {} could not be updated", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("client id = {} updated: {}", id, client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/filter-paginated/{name}&page={page}&size={size}", method = RequestMethod.GET)
    public ClientsDTO filterClientsByNamePaginated(@PathVariable String name, @PathVariable int page, @PathVariable int size) {
        log.trace("filtered clients by name = {} page = {} size = {}", name, page, size);
        return new ClientsDTO(
                clientConverter.toDTOList(
                        clientService.filterClientsByNamePaginated(name, page, size)
                )
        );
    }

    @RequestMapping(value = "/clients/filter-name/{name}", method = RequestMethod.GET)
    public ClientsDTO filterClientsByName(@PathVariable String name) {
        log.trace("filtered clients by name = {}", name);
        return new ClientsDTO(
                clientConverter.toDTOList(
                        clientService.filterClientsByName(name)
                )
        );
    }

    @RequestMapping(value = "/clients/filter-fidelity/{fidelity}", method = RequestMethod.GET)
    public ClientsDTO filterClientsByFidelity(@PathVariable int fidelity) {
        log.trace("filtered clients by fidelity = {}", fidelity);
        return new ClientsDTO(
                clientConverter.toDTOList(
                        clientService.filterClientsByFidelity(fidelity)
                )
        );
    }
}

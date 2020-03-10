package ro.sdi.lab24.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sdi.lab24.exception.AlreadyExistingElementException;
import ro.sdi.lab24.exception.ElementNotFoundException;
import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.repository.MemoryRepository;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.validation.ClientValidator;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientControllerTest {
    private Repository<Integer, Client> repo;
    private ClientController controller;
    private ClientValidator validator;

    private long length(Iterable<Client> list) {
        return StreamSupport.stream(list.spliterator(), false).count();
    }

    @BeforeEach
    void setUp() {
        validator = new ClientValidator();
        repo = new MemoryRepository<Integer, Client>();
        controller = new ClientController(repo, validator);
    }

    @Test
    void addClient() {
        Client client, clientA, clientB, client2;

        Iterable<Client> list = controller.getClients();
        assertEquals(0, length(list));

        controller.addClient(1, "a");
        clientA = new Client(1, "a");
        list = controller.getClients();
        assertEquals(1, length(list));

        client = list.iterator().next();
        assertEquals(clientA, client);

        controller.addClient(2, "b");
        clientB = new Client(2, "b");
        list = controller.getClients();
        assertEquals(2, length(list));

        Iterator<Client> it = list.iterator();
        client = it.next();
        client2 = it.next();
        assertEquals(client, clientA);
        assertEquals(client2, clientB);

        assertThrows(AlreadyExistingElementException.class, () -> controller.addClient(1, "c"));
    }

    @Test
    void deleteClient() {
        Client client = new Client(2, "b");
        controller.addClient(1, "a");
        controller.addClient(2, "b");
        assertEquals(length(controller.getClients()), 2);

        controller.deleteClient(1);
        assertEquals(length(controller.getClients()), 1);

        assertThrows(ElementNotFoundException.class, () -> controller.deleteClient(1));
        assertEquals(length(controller.getClients()), 1);
        assertEquals(controller.getClients().iterator().next(), client);
    }

    @Test
    void getClients() {
        controller.addClient(1, "a");
        controller.addClient(2, "b");
        assertEquals(length(controller.getClients()), 2);

        controller.deleteClient(1);
        assertEquals(length(controller.getClients()), 1);

        controller.deleteClient(2);
        assertEquals(length(controller.getClients()), 0);
    }

    @Test
    void updateClient() {
        controller.addClient(1, "a");
        controller.addClient(2, "b");

        Client client = new Client(1, "c");

        controller.updateClient(1, "c");
        assertEquals(controller.getClients().iterator().next(), client);
    }

    @Test
    void filterClientsByName() {
        controller.addClient(1, "a");
        controller.addClient(2, "b");
        controller.addClient(3, "c");
        controller.addClient(4, "aa");
        controller.addClient(5, "bb");

        Client c1 = new Client(1, "a");
        Client c2 = new Client(2, "b");
        Client c3 = new Client(3, "c");
        Client c4 = new Client(4, "aa");
        Client c5 = new Client(5, "bb");

        Iterable<Client> iterable = controller.filterClientsByName("a");
        Iterator<Client> iterator = iterable.iterator();
        assertEquals(length(iterable), 2);
        assertEquals(iterator.next(), c1);
        assertEquals(iterator.next(), c4);

        iterable = controller.filterClientsByName("z");
        assertEquals(length(iterable), 0);

        iterable = controller.filterClientsByName("c");
        iterator = iterable.iterator();
        assertEquals(length(iterable), 1);
        assertEquals(iterator.next(), c3);
    }
}
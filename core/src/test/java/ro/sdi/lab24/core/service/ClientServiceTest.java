package ro.sdi.lab24.core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.sdi.lab24.core.ITConfig;
import ro.sdi.lab24.core.model.Client;

import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-clients.xml")
public class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @Test
    public void getClients() throws Exception {
        Iterable<Client> clients = clientService.getClients();
        long size = StreamSupport.stream(clients.spliterator(), false).count();
        assertEquals("there should be 3 clients", 3, size);
    }

    @Test
    public void addClient() throws Exception {
        clientService.addClient("Marius", 3);
        Iterable<Client> clients = clientService.getClients();
        long size = StreamSupport.stream(clients.spliterator(), false).count();

        assertEquals("added new client, 4 instead of 3  clients", 4, size);
    }

    @Test
    public void deleteClient() throws Exception {
        clientService.deleteClient(1);
        Iterable<Client> clients = clientService.getClients();
        long size = StreamSupport.stream(clients.spliterator(), false).count();

        assertEquals("deleted a client", 2, size);
    }

    @Test
    public void updateClient() throws Exception {
        clientService.updateClient(1, "Razvanel", 5);
        Iterable<Client> clients = clientService.getClients();
        long size = StreamSupport.stream(clients.spliterator(), false).count();
        assertEquals("still 3 clients after update", 3, size);

        Client clientRazvanel = StreamSupport.stream(clients.spliterator(), false)
                .filter(client -> client.getId() == 1).findFirst().orElseThrow(RuntimeException::new);
        assertEquals("new name", clientRazvanel.getName(), "Razvanel");
        assertEquals("new fidelity", clientRazvanel.getFidelity(), Integer.valueOf(5));
    }

    @Test
    public void filterClientsByName() throws Exception {
        Iterable<Client> clients = clientService.filterClientsByName("Razvan");
        long size = StreamSupport.stream(clients.spliterator(), false).count();
        assertEquals("only one result", 1, size);
        assertEquals("client with name Razvan", clients.iterator().next().getName(), "Razvan");
    }

    @Test
    public void filterClientsByFidelity() throws Exception {
        Iterable<Client> clients = clientService.filterClientsByFidelity(3);
        long size = StreamSupport.stream(clients.spliterator(), false).count();

        assertEquals("only one result", size, 1);
        assertEquals("only Damn", clients.iterator().next().getName(), "Damn");
    }
}

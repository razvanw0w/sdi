package ro.sdi.lab24.core.repository;

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
import org.springframework.transaction.annotation.Transactional;
import ro.sdi.lab24.core.ITConfig;
import ro.sdi.lab24.core.model.Client;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-clients.xml")
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void findAll() throws Exception {
        List<Client> all = clientRepository.findAll();
        assertEquals("there should be 3 clients", 3, all.size());
    }

    @Test
    public void add() throws Exception {
        Client client = Client.builder()
                .name("Mock")
                .fidelity(3)
                .build();
        client.setId(-1);
        clientRepository.save(client);
        List<Client> all = clientRepository.findAll();
        assertEquals("there should be 4 clients", 4, all.size());


    }

    @Test
    public void delete() throws Exception {
        clientRepository.deleteById(2);
        List<Client> all = clientRepository.findAll();
        assertEquals("there should be 2 clients", 2, all.size());
    }

    @Test
    @Transactional
    public void update() throws Exception {
        Client client = clientRepository.findById(1).orElseThrow(RuntimeException::new);
        client.setName("updated");
        List<Client> all = clientRepository.findAll();
        assertEquals("updated entity", all.get(0).getName(), "updated");
    }
}

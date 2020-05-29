package ro.sdi.lab24.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.core.service.ClientService;
import ro.sdi.lab24.web.converter.ClientConverter;
import ro.sdi.lab24.web.dto.ClientDTO;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClientRestControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private ClientRestController clientRestController;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientConverter clientConverter;

    private Client client1;
    private Client client2;
    private ClientDTO clientDTO1;
    private ClientDTO clientDTO2;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(clientRestController).build();
        initData();
    }

    @Test
    public void getClients() throws Exception {
        List<Client> clients = Arrays.asList(client1, client2);
        List<ClientDTO> clientDTOS = Arrays.asList(clientDTO1, clientDTO2);

        when(clientService.getClients()).thenReturn(clients);
        when(clientConverter.toDTOList(clients)).thenReturn(clientDTOS);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.clients", hasSize(2)))
                .andExpect(jsonPath("$.clients[0].name", is("c1")))
                .andExpect(jsonPath("$.clients[1].name", is("c2")))
                .andExpect(jsonPath("$.clients[0].fidelity", is(3)))
                .andExpect(jsonPath("$.clients[1].fidelity", is(1)));

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);

        verify(clientService, times(1)).getClients();
        verify(clientConverter, times(1)).toDTOList(clients);
        verifyNoMoreInteractions(clientService, clientConverter);
    }

    @Test
    public void updateClient() throws Exception {
        when(clientConverter.toModel(clientDTO1)).thenReturn(client1);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/clients/{id}", client1.getId(), clientDTO1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(clientDTO1)))
                .andExpect(status().isOk());
        verify(clientService, times(1)).updateClient(client1.getId(), client1.getName(), client1.getFidelity());
        verify(clientConverter, times(1)).toModel(clientDTO1);
        verifyNoMoreInteractions(clientService, clientConverter);
    }

    @Test
    public void addClient() throws Exception {
        Client newClient = Client.builder().name("new").fidelity(3).build();
        newClient.setId(5);
        ClientDTO newClientDTO = createClientDTO(newClient);

        when(clientConverter.toModel(newClientDTO)).thenReturn(newClient);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/clients", newClientDTO)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(newClientDTO)))
                .andExpect(status().isOk());
        verify(clientService, times(1)).addClient(newClient.getName(), newClient.getFidelity());
        verify(clientConverter, times(1)).toModel(newClientDTO);
        verifyNoMoreInteractions(clientService, clientConverter);
    }

    @Test
    public void deleteClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/clients/{id}", client1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(clientService, times(1)).deleteClient(client1.getId());
    }

    private void initData() {
        client1 = Client.builder().name("c1").fidelity(3).build();
        client1.setId(1);
        client2 = Client.builder().name("c2").fidelity(1).build();
        client2.setId(2);

        clientDTO1 = createClientDTO(client1);
        clientDTO2 = createClientDTO(client2);
    }

    private ClientDTO createClientDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .fidelity(client.getFidelity())
                .build();
    }

    private String toJsonString(ClientDTO clientDTO) {
        try {
            return new ObjectMapper().writeValueAsString(clientDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

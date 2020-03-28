package ro.sdi.lab24.server;

import org.junit.jupiter.api.Test;

import ro.sdi.lab24.controller.ClientController;
import ro.sdi.lab24.controller.ClientControllerImpl;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.repository.MemoryRepository;
import ro.sdi.lab24.validation.ClientValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerAdapterTest
{

    @Test
    void handle()
    {
        ClientController clientController = new ClientControllerImpl(
                new MemoryRepository<>(),
                new ClientValidator()
        );
        Message message = new Message("ClientController:addClient", "1", "nume");
        Message response = ControllerAdapter.handle(message, clientController);
        assertEquals("success", response.getHeader());

        message = new Message("ClientController:getClients");
        response = ControllerAdapter.handle(message, clientController);
        assertEquals("success", response.getHeader());

        message = new Message("ClientController:deleteClient", "1");
        response = ControllerAdapter.handle(message, clientController);
        assertEquals("success", response.getHeader());

        message = new Message("ClientController:getClients");
        response = ControllerAdapter.handle(message, clientController);
        assertEquals("success", response.getHeader());
    }
}
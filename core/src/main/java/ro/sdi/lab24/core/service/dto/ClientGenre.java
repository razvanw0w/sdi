package ro.sdi.lab24.core.service.dto;

import ro.sdi.lab24.core.model.Client;

import java.io.Serializable;

public class ClientGenre implements Serializable {
    private Client client;
    private String genre;

    public ClientGenre(Client client, String genre) {
        this.client = client;
        this.genre = genre;
    }

    public Client getClient()
    {
        return client;
    }

    public String getGenre()
    {
        return genre;
    }
}
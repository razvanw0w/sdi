package ro.sdi.lab24.controller.dto;

import ro.sdi.lab24.model.Client;

public class ClientGenre
{
    private Client client;
    private String genre;

    public ClientGenre(Client client, String genre)
    {
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
package ro.sdi.lab24.model;

public class Client extends Entity<Integer>
{
    private String name;

    public Client(int id, String name)
    {
        super(id);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return String.format("Client[%d, %s]", id, name);
    }
}

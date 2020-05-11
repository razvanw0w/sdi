package ro.sdi.lab24.core.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@javax.persistence.Entity
public class Client extends Entity<Integer> implements Serializable {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String name;

    public Client() {
        super(0);
    }

    public Client(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Client[%d, %s]", id, name);
    }
}

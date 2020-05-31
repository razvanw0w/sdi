package ro.sdi.lab24.core.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "app_user")
@Data
public class User extends ro.sdi.lab24.core.model.Entity<Integer> {
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;
}

package ro.sdi.lab24.core.repository;

import org.springframework.data.repository.query.Param;
import ro.sdi.lab24.core.model.Client;

import java.util.List;

public interface ClientCustomRepository {
    List<Client> findByExactName(@Param("name") String name);

    List<Client> findByExactFidelity(@Param("fidelity") Integer fidelity);
}

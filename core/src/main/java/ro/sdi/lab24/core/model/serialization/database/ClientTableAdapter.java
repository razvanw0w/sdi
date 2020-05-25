package ro.sdi.lab24.core.model.serialization.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sdi.lab24.core.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientTableAdapter extends TableAdapter<Integer, Client> {
    @Query("select distinct client from Client client")
    @EntityGraph(value = "clientWithRentalsAndMovies", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllClientsWithRentals();

    @Query("select distinct client from Client client where client.id=:clientID")
    @EntityGraph(value = "clientWithRentalsAndMovies", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Client> findByIdWithRentals(@Param("clientID") Integer clientID);

//    Page<Client> findAllWithRentals(Pageable pageable);

//    Client findByIdWithRentals()
}

package ro.sdi.lab24.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sdi.lab24.core.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends Repository<Integer, Client> {
    @Query("select distinct client from Client client")
    @EntityGraph(value = "clientWithRentalsAndMovies", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllClientsWithRentals();

    @Query("select distinct client from Client client where client.id=:clientID")
    @EntityGraph(value = "clientWithRentalsAndMovies", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Client> findByIdWithRentals(@Param("clientID") Integer clientID);

//    Page<Client> findAllWithRentals(Pageable pageable);

//    Client findByIdWithRentals()
}

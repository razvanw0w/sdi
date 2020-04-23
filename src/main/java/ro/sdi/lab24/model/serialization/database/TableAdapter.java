package ro.sdi.lab24.model.serialization.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ro.sdi.lab24.model.Entity;

import java.io.Serializable;

@NoRepositoryBean
public interface TableAdapter<ID extends Serializable, T extends Entity<ID>>
        extends JpaRepository<T, ID> {

}

package ro.sdi.lab24.core.model.serialization.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ro.sdi.lab24.core.model.Entity;

import java.io.Serializable;

@NoRepositoryBean
public interface TableAdapter<ID extends Serializable, T extends Entity<ID>>
        extends JpaRepository<T, ID> {

}

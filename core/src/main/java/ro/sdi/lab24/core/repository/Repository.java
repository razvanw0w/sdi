package ro.sdi.lab24.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ro.sdi.lab24.core.model.Entity;

import java.io.Serializable;

@NoRepositoryBean
public interface Repository<ID extends Serializable, T extends Entity<ID>>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}

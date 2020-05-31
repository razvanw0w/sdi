package ro.sdi.lab24.core.repository;

import org.springframework.data.jpa.repository.Query;
import ro.sdi.lab24.core.model.User;

public interface UserRepository extends Repository<Integer, User> {
    @Query("select u from User u where u.username=?1")
    User getUserByUsername(String userName);
}

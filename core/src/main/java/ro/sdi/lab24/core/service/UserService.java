package ro.sdi.lab24.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sdi.lab24.core.model.User;
import ro.sdi.lab24.core.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String userName) {
        return userRepository.getUserByUsername(userName);
    }
}

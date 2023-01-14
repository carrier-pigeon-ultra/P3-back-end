package com.revature.services;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) {
        if (isValidUser(user)) {
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }



    private boolean isValidUser(User user) {
        return (user.getFirstName() != null && user.getLastName() != null && user.getEmail() != null
                        && user.getPassword() != null);
    }


}

package com.revature.services;

import com.revature.exceptions.EmptyFieldsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WeakPasswordException;
import com.revature.models.User;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> findByCredentials(String email, String password) throws UserNotFoundException {
        Optional<User> ret = userService.findByCredentials(email, password);

        if(ret.orElse(null) == null) {
            throw new UserNotFoundException();
        } else {
            return ret;
        }

    }

    public User register(User user) throws WeakPasswordException, EmptyFieldsException {
        return userService.save(user);
    }
}

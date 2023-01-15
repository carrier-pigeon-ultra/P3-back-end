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



    public boolean isValidUser(User user) {
        return (user.getFirstName() != null && user.getLastName() != null && user.getEmail() != null
                        && strongPassword(user.getPassword())) || (user.getEmail().length() < 5 );
    }


    public boolean strongPassword(String password) {

        // Confirm password is more than 8 characters long.
        if (password==null || password.length() < 8) {
            return false;
        }

        // Confirm password contains 1 or more numbers, one uppercase character, and one lowercase character.
        boolean hasNumber = false, hasUppercaseChar = false, hasLowercaseChar = false;
        for(char c: password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercaseChar = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercaseChar = true;
            }
        }

        return hasNumber && hasUppercaseChar && hasLowercaseChar;
    }


}

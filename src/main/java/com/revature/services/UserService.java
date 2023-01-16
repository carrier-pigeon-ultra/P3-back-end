package com.revature.services;

import com.revature.exceptions.EmptyFieldsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WeakPasswordException;
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

    public User save(User user) throws WeakPasswordException, EmptyFieldsException {
        if (isValidUser(user)) {
            return userRepository.save(user);
        } else {
            throw new EmptyFieldsException();
        }
    }



    public boolean isValidUser(User user) throws WeakPasswordException {

        if (!strongPassword(user.getPassword())) {
            throw new WeakPasswordException();
        }

        return (validStringInput(user.getFirstName()) && validStringInput(user.getLastName())
                && validStringInput(user.getEmail()));
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

    public boolean  validStringInput(String str) {
        return str !=null && str.length() > 2;
     }

    public void updateResetPassword(String token, String email) throws UserNotFoundException {
    User user = userRepository.findUserByEmail(email);

    if (user != null){
        user.setResetPasswordToken(token);
        userRepository.save(user);
        }else {
        throw new UserNotFoundException("Could not find any user with the email of" + email);
    }
    }
    public User get(String token){
        return userRepository.findUserByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword){
        user.setPassword(newPassword);
        user.setResetPasswordToken(null);

        userRepository.save(user);
    }
}

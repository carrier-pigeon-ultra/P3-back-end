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
        return userRepository.save(user);
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

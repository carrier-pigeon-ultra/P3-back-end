package com.revature.controllers;

import com.revature.dtos.ResetPasswordRequest;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class ResetPasswordController {

    UserService userService;
    URL url;

    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){

        String newPassword = resetPasswordRequest.getPassword();
        User user = userService.get(url.getFile());
        System.out.println(user.toString());

        userService.updatePassword(user,newPassword);

    }
}
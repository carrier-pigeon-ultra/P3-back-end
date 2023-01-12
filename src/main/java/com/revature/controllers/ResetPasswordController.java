package com.revature.controllers;

import com.revature.dtos.ResetPasswordRequest;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/reset-password-form")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://localhost:5000"}, allowCredentials = "true")
public class ResetPasswordController {

    UserService userService;
//    URL url;

    @Autowired
    public ResetPasswordController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/{token}")
//    @PostMapping("/token")
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest, @PathVariable String token) throws IOException {

//        url.openStream();
        User user = userService.get(token);
        String newPassword = resetPasswordRequest.getPassword();

        System.out.println(resetPasswordRequest);
        System.out.println(token);



//        System.out.println(user.toString());

        userService.updatePassword(user,newPassword);

    }
}
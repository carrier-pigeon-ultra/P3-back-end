package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.exceptions.EmptyFieldsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WeakPasswordException;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000",
        "http://codepipeline-us-west-2-791209503483.s3-website-us-west-2.amazonaws.com",
        "http://carrier-pigeon-client-not-pipline.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true")
public class ProfileController {

    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @Authorized
    @PutMapping("/profile")
    ResponseEntity<User> updateUserProfile(@RequestBody User user){
        try {
            return ResponseEntity.ok(this.userService.save(user));
        } catch(EmptyFieldsException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        } catch (WeakPasswordException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }


}

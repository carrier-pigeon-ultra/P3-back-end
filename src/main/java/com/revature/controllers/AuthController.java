package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.SearchResponse;
import com.revature.exceptions.EmptyFieldsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WeakPasswordException;
import com.revature.models.User;
import com.revature.models.UserView;
import com.revature.repositories.UserRepository;
import com.revature.services.AuthService;
import com.revature.services.SearchService;
import com.revature.services.SearchServiceImplementation;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class AuthController {


    private final AuthService authService;
    private final UserService userService;

    private final SearchService searchService;

    @Autowired
    public AuthController(AuthService authService, SearchServiceImplementation searchService, UserService userService) {
        this.authService = authService;
        this.searchService = searchService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) throws UserNotFoundException {

        Optional<User> optional;

        try {
            optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }


        session.setAttribute("user", optional.get());

        return ResponseEntity.ok(optional.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User created = new User(0,
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getBirthday(),
                "",
                "",
                "");

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(created));
        } catch (WeakPasswordException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        } catch (EmptyFieldsException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //Search other users controller
    @GetMapping("/users/search")
    public ResponseEntity<?> searchUser(@RequestParam("searchText") String searchText,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page-1;
        size = size <= 0 ? 5 : size;
        List<SearchResponse> userSearchResult = searchService.getUserSearchResult(searchText, page, size);
        return new ResponseEntity<>(userSearchResult, HttpStatus.OK);
    }
    @GetMapping("/users/search/{userId}")
    public ResponseEntity<SearchResponse> getUserById(@PathVariable("userId") int userId) {

        User targetedUser;

        try {
            targetedUser =searchService.getUserById(userId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(new SearchResponse(targetedUser));
    }
}

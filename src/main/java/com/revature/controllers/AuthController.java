package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.SearchResponse;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.AuthService;
import com.revature.services.SearchService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class AuthController {

    @Autowired
    private final AuthService authService;
    private UserRepository userRepository;
    private UserService userService;
    @Autowired
    private SearchService searchService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        if(!optional.isPresent()) {
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

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(created));
    }

    //Search other users controller
    @GetMapping("/users/search")
    public ResponseEntity<List<SearchResponse>> searchUser(@RequestParam("searchText") String searchText,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page-1;
        size = size <= 0 ? 5 : size;
        List<SearchResponse> userSearchResult = searchService.getUserSearchResult(searchText, page, size);
        return new ResponseEntity<>(userSearchResult, HttpStatus.OK);
    }
    @GetMapping("/users/search/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") int userId) {
       // User authUser = searchService.getAuthenticatedUser();
        User targetedUser = searchService.getUserById(userId);
        return new ResponseEntity<>(targetedUser, HttpStatus.OK);
    }
}

package com.revature.services;

import com.revature.dtos.SearchResponse;
import com.revature.exceptions.InvalidOperationException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchServiceImplementation implements SearchService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public SearchServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<SearchResponse> getUserSearchResult(String searchText, Integer page, Integer size)  {
        if (searchText.length() == 0) throw new InvalidOperationException();
        List<User> result =  userRepository.findUsersByName(searchText, PageRequest.of(page,size));
        if (result.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found for " + " " + searchText);
        return result.stream().map(this::userToSearchResponse).collect(Collectors.toList());

    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

//    public final User getAuthenticatedUser() {
//        String authUserEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        return getUserByEmail(authUserEmail);
//    }

    private SearchResponse userToSearchResponse(User user) {
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setId(user.getId());
        searchResponse.setEmail(user.getEmail());
        searchResponse.setFirstName(user.getFirstName());
        searchResponse.setLastName(user.getLastName());
        return searchResponse;
    }
}

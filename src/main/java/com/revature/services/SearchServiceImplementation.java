package com.revature.services;

import com.revature.dtos.SearchResponse;
import com.revature.exceptions.InvalidOperationException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchServiceImplementation implements SearchService {
    private final UserRepository userRepository;

    @Autowired
    public SearchServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<SearchResponse> getUserSearchResult(String searchText, Integer page, Integer size) {
        if (searchText.length() < 0) throw new InvalidOperationException();

        return userRepository.findUsersByName(
                searchText, PageRequest.of(page, size)
        ).stream().map(this::userToSearchResponse).collect(Collectors.toList());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public final User getAuthenticatedUser() {
        String authUserEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return getUserByEmail(authUserEmail);
    }

    private SearchResponse userToSearchResponse(User user) {
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setId(user.getId());
        searchResponse.setEmail(user.getEmail());
        searchResponse.setFirstName(user.getFirstName());
        searchResponse.setLastName(user.getLastName());
        return searchResponse;
    }
}

package com.revature.services;

import com.revature.dtos.SearchResponse;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;

import java.util.List;

public interface SearchService {
    List<SearchResponse> getUserSearchResult(String searchText, Integer page, Integer size);
    User getUserByEmail(String email) throws UserNotFoundException;

    User getUserById(int id) throws UserNotFoundException;

}

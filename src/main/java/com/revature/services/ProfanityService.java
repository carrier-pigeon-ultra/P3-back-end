package com.revature.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProfanityService {

    public ProfanityService(){}

    public String censorString(String input) {
        String profanityURI = "https://www.purgomalum.com/service/plain?text=";
        RestTemplate externalProfanityFilter = new RestTemplate();
        String response = externalProfanityFilter.getForObject(profanityURI + input, String.class);
        return response;
    }

}

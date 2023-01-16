package com.revature.services;

import com.revature.models.Post;
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

    public void censorPostAndChildComments(Post post) {
        post.setText(censorString(post.getText()));

        if(post.getComments()==null) {
            return;
        }

        for(Post comment:post.getComments()) {
            censorPostAndChildComments(comment);
        }

    }

}

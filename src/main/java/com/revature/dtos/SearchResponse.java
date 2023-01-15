package com.revature.dtos;


import com.revature.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthday;

    private String hometown;
    private String currentResidence;

    private String biography;

    public SearchResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthday = user.getBirthday();
        this.hometown = user.getHometown();
        this.currentResidence = user.getCurrentResidence();
        this.biography = user.getBiography();
    }
}

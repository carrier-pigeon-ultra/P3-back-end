package com.revature.models;

import com.revature.Security.SecurityUtils;
import lombok.Data;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.logging.Logger;

@Data
public class UserView implements Serializable {
    private  static final Logger log = (Logger) LoggerFactory.getLogger(UserView.class);
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public UserView(User user) {
        final User profile = SecurityUtils.currentProfile();
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

}

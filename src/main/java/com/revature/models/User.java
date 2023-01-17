package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;


    @Column(name = "email", nullable = false, unique = true)

    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Date birthday;
    private String hometown;
    private String currentResidence;
    private String biography;


    @Column(name = "reset_password_token")
    private  String resetPasswordToken;

    public User(int id, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

    }


    public User(int id, String email, String password, String firstName, String lastName,Date birthday, String hometown, String currentResidence, String biography) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.hometown = hometown;
        this.currentResidence = currentResidence;
        this.biography = biography;
        this.resetPasswordToken = null;

    }


}

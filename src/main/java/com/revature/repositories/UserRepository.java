package com.revature.repositories;

import com.revature.dtos.SearchResponse;
import com.revature.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);
//    @Query("SELECT u FROM users u WHERE u.email = ?1")
//    User findByEmailContaining(String email);

    @Query(value = "select * from users u " +
            "where concat(u.first_name, ' ', u.last_name) like %:name% " +
            "order by u.first_name asc, u.last_name asc",
            nativeQuery = true)
    List<User> findUsersByName(String name, Pageable pageable);


    User findUserByEmail(String email);
    User findUserByResetPasswordToken(String token);
//    void enable(Integer id);
}

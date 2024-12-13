package com.example.demo.repository;

import com.example.demo.dto.response.UserProfileResponse;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByEmailEqualsIgnoreCase(String email);

    @Query("SELECT u from User u")
    List<User> findAllUsers();

    @Query("""
            select  new com.example.demo.dto.response.UserProfileResponse(
                        u.id, u.name, u.email, u.role
                        )
                  from User u where u.email = :email
            """)
    UserProfileResponse getProfileByEmail(String email);
}
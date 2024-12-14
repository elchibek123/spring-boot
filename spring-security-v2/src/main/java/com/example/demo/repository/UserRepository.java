package com.example.demo.repository;

import com.example.demo.dto.response.UserProfileResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    default User findByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email: " + email + " not found")
        );
    }

    @Query("SELECT u from User u")
    List<User> findAllUsers();

    @Query("""
            select  new com.example.demo.dto.response.UserProfileResponse(
                        u.id, u.name, u.email, u.role
                        )
                  from User u where u.email = :email
            """)
    UserProfileResponse getProfileByEmail(String email);

    default User findOrElseThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException(String.format("User with id: %s not found", id)));
    }
}
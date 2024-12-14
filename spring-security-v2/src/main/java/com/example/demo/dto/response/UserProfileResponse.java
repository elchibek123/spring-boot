package com.example.demo.dto.response;

import com.example.demo.enums.Role;
import com.example.demo.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileResponse {
    private String title = "USER PROFILE";
    private Long id;
    private String name;
    private String email;
    private     Role role;

    public UserProfileResponse(Long id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UserProfileResponse entityToDto(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setRole(user.getRole());
        return this;
    }
}
package java15.projectrestaurant.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java15.projectrestaurant.enums.Role;
import java15.projectrestaurant.model.User;
import java15.projectrestaurant.validation.annotation.ValidPassword;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public record UpdateUserRequest(
        @Size(min = 3, max = 20, message = "Name should be between 3 and 20 characters")
        String firstName,

        String lastName,
        LocalDate dateOfBirth,

        @ValidPassword
        String password,

        String phoneNumber,

        @Enumerated(EnumType.STRING)
        Role role,

        @PositiveOrZero
        int experience
) {
    public User toEntity(User user, PasswordEncoder passwordEncoder) {
        if (this.firstName != null) {
            user.setFirstName(this.firstName);
        }

        if (this.lastName != null) {
            user.setLastName(this.lastName);
        }

        if (this.dateOfBirth != null) {
            user.setDateOfBirth(this.dateOfBirth);
        }

        if (this.password != null) {
            String encodedPassword = passwordEncoder.encode(this.password);
            user.setPassword(encodedPassword);
        }

        if (this.phoneNumber != null) {
            user.setPhoneNumber(this.phoneNumber);
        }

        if (this.role != null) {
            user.setRole(this.role);
        }

        if (this.experience != 0) {
            user.setExperience(this.experience);
        }

        return user;
    }
}
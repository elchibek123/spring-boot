package java15.projectrestaurant.dto.request;

import jakarta.validation.constraints.*;
import java15.projectrestaurant.model.User;
import java15.projectrestaurant.validation.annotation.ValidPassword;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank
        @Size(min = 3, max = 20, message = "Name should be between 3 and 20 characters")
        String firstName,

        @NotBlank
        String lastName,

        @NotNull
        LocalDate dateOfBirth,

        @NotBlank
        @Email(message = "Invalid email")
        String email,

        @NotBlank
        @ValidPassword
        String password,

        @NotBlank
        @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Invalid phone number format")
        String phoneNumber
) {
    public User toEntity(User user, PasswordEncoder passwordEncoder) {
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setDateOfBirth(this.dateOfBirth);
        user.setEmail(this.email);
        user.setPassword(passwordEncoder.encode(this.password));
        user.setPhoneNumber(this.phoneNumber);

        return user;
    }
}

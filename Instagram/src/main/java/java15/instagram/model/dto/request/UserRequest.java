package java15.instagram.model.dto.request;

import jakarta.validation.constraints.*;
import java15.instagram.model.entity.User;
import java15.instagram.service.validation.annotation.ValidPassword;
import java15.instagram.util.CommonConstants;

public record UserRequest(
        @NotBlank
        String username,

        @NotBlank
        @Email(message = CommonConstants.EMAIL_ERROR_MESSAGE)
        String email,

        @NotBlank
        @ValidPassword
        String password,

        @NotBlank
        @Pattern(regexp = CommonConstants.PHONE_NUMBER_REGEX, message = CommonConstants.PHONE_NUMBER_ERROR_MESSAGE)
        String phoneNumber
) {
    public User toEntity() {
        User user = new User();
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPhoneNumber(this.phoneNumber);

        return user;
    }
}

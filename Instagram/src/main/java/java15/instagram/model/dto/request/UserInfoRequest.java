package java15.instagram.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import java15.instagram.model.entity.UserInfo;
import java15.instagram.model.enums.Gender;
import org.hibernate.validator.constraints.URL;

public record UserInfoRequest(
        @NotBlank
        String fullName,

        String biography,
        Gender gender,

        @URL(message = "Must be a valid URL")
        String image
) {
    public UserInfo toEntity() {
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(this.fullName);
        userInfo.setBiography(this.biography);
        userInfo.setGender(this.gender);
        userInfo.setImage(this.image);

        return userInfo;
    }
}

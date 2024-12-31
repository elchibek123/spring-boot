package java15.instagram.model.dto.request;

import java15.instagram.model.entity.UserInfo;
import java15.instagram.model.enums.Gender;
import org.hibernate.validator.constraints.URL;

public record UpdateUserInfoRequest(
        String fullName,
        String biography,
        Gender gender,

        @URL(message = "Must be a valid URL")
        String image
) {
    public UserInfo toEntity(UserInfo existingUserInfo) {
        if (existingUserInfo == null) {
            existingUserInfo = new UserInfo();
        }

        if (fullName != null) {
            existingUserInfo.setFullName(fullName);
        }

        if (biography != null) {
            existingUserInfo.setBiography(biography);
        }

        if (gender != null) {
            existingUserInfo.setGender(gender);
        }

        if (image != null) {
            existingUserInfo.setImage(image);
        }

        return existingUserInfo;
    }
}

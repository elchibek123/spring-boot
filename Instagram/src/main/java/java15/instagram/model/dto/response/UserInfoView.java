package java15.instagram.model.dto.response;

import java15.instagram.model.entity.UserInfo;
import java15.instagram.model.enums.Gender;

public record UserInfoView(
        Long id,
        String fullName,
        String biography,
        Gender gender,
        String imageUrl
) {
    public static UserInfoView fromUserInfo(UserInfo userInfo) {
        return new UserInfoView(
                userInfo.getId(),
                userInfo.getFullName(),
                userInfo.getBiography(),
                userInfo.getGender(),
                userInfo.getImage()
        );
    }
}


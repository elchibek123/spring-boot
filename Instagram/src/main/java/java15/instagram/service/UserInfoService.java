package java15.instagram.service;

import java15.instagram.model.dto.request.UpdateUserInfoRequest;
import java15.instagram.model.dto.request.UserInfoRequest;
import java15.instagram.model.dto.response.UserInfoView;
import java15.instagram.model.entity.UserInfo;

public interface UserInfoService {
    UserInfoView saveUserInfo(Long userId, UserInfoRequest request);

    UserInfo findByUserId(Long userId);

    UserInfo updateUserInfo(Long userId, UpdateUserInfoRequest request);

    UserInfo changeImage(Long userId, String imageUrl);

    UserInfo deleteImage(Long userId);
}

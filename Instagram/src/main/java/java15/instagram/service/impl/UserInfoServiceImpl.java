package java15.instagram.service.impl;

import java15.instagram.exception.NotFoundException;
import java15.instagram.exception.ValidationException;
import java15.instagram.model.dto.request.UpdateUserInfoRequest;
import java15.instagram.model.dto.request.UserInfoRequest;
import java15.instagram.model.dto.response.UserInfoView;
import java15.instagram.model.entity.User;
import java15.instagram.model.entity.UserInfo;
import java15.instagram.repository.UserInfoRepository;
import java15.instagram.repository.UserRepository;
import java15.instagram.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    @Override
    public UserInfoView saveUserInfo(Long userId, UserInfoRequest request) {
        User user = userRepository.findByIdOrThrow(userId);

        UserInfo userInfo = request.toEntity();

        user.setUserInfo(userInfo);

        return UserInfoView.fromUserInfo(userInfoRepository.save(userInfo));
    }

    @Override
    public UserInfo findByUserId(Long userId) {
        return userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("UserInfo not found for userId: " + userId));
    }

    @Override
    public UserInfo updateUserInfo(Long userId, UpdateUserInfoRequest request) {
        User user = userRepository.findByIdOrThrow(userId);

        UserInfo userInfo = user.getUserInfo();
        UserInfo updatedUserInfo = request.toEntity(userInfo);

        user.setUserInfo(updatedUserInfo);

        userRepository.save(user);

        return updatedUserInfo;
    }

    @Override
    public UserInfo changeImage(Long userId, String imageUrl) {
        User user = userRepository.findByIdOrThrow(userId);

        UserInfo userInfo = user.getUserInfo();
        if (userInfo == null) {
            throw new ValidationException("UserInfo does not exist for this user.");
        }

        userInfo.setImage(imageUrl);
        userRepository.save(user);

        return userInfo;
    }

    @Override
    public UserInfo deleteImage(Long userId) {
        User user = userRepository.findByIdOrThrow(userId);

        UserInfo userInfo = user.getUserInfo();
        if (userInfo == null) {
            throw new ValidationException("UserInfo does not exist for this user.");
        }

        userInfo.setImage(null);
        userRepository.save(user);

        return userInfo;
    }
}

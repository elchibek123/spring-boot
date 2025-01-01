package java15.instagram.controller;

import jakarta.validation.Valid;
import java15.instagram.model.dto.request.UpdateUserInfoRequest;
import java15.instagram.model.dto.request.UserInfoRequest;
import java15.instagram.model.dto.response.UserInfoView;
import java15.instagram.model.entity.UserInfo;
import java15.instagram.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-info")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @PostMapping("/{userId}")
    public ResponseEntity<UserInfoView> createUserInfo(
            @PathVariable Long userId,
            @RequestBody @Valid UserInfoRequest request) {
        UserInfoView userInfoView = userInfoService.saveUserInfo(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfoView);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoView> findUserInfoByUserId(@PathVariable Long userId) {
        UserInfo userInfo = userInfoService.findByUserId(userId);
        return ResponseEntity.ok(UserInfoView.fromUserInfo(userInfo));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserInfoView> updateUserInfo(
            @PathVariable Long userId,
            @RequestBody @Valid UpdateUserInfoRequest request) {
        UserInfo updatedUserInfo = userInfoService.updateUserInfo(userId, request);
        return ResponseEntity.ok(UserInfoView.fromUserInfo(updatedUserInfo));
    }

    @PatchMapping("/{userId}/change-image")
    public ResponseEntity<UserInfoView> changeImage(
            @PathVariable Long userId,
            @RequestParam String imageUrl) {
        UserInfo updatedUserInfo = userInfoService.changeImage(userId, imageUrl);
        return ResponseEntity.ok(UserInfoView.fromUserInfo(updatedUserInfo));
    }

    @DeleteMapping("/{userId}/delete-image")
    public ResponseEntity<UserInfoView> deleteImage(@PathVariable Long userId) {
        UserInfo updatedUserInfo = userInfoService.deleteImage(userId);
        return ResponseEntity.ok(UserInfoView.fromUserInfo(updatedUserInfo));
    }
}

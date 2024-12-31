package java15.instagram.service;

import java15.instagram.model.dto.request.UserRequest;
import java15.instagram.model.dto.response.SimpleResponseMessage;
import java15.instagram.model.dto.response.UserProfileView;
import java15.instagram.model.entity.User;

import java.util.List;

public interface UserService {
    SimpleResponseMessage register(UserRequest request);

    UserProfileView getUserProfile(String username);

    List<User> searchUsers(String searchTerm);
}

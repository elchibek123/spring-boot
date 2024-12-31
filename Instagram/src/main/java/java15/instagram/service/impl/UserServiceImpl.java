package java15.instagram.service.impl;

import java15.instagram.model.dto.request.UserRequest;
import java15.instagram.model.dto.response.SimpleResponseMessage;
import java15.instagram.model.dto.response.UserProfileView;
import java15.instagram.model.entity.Follower;
import java15.instagram.model.entity.Post;
import java15.instagram.model.entity.User;
import java15.instagram.model.enums.Role;
import java15.instagram.repository.UserRepository;
import java15.instagram.service.UserService;
import java15.instagram.service.validation.validator.UserRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRequestValidator userRequestValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public SimpleResponseMessage register(UserRequest request) {
        userRequestValidator.validate(request, UserRequest::email, UserRequest::username);

        User user = request.toEntity();
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        Follower follower = new Follower();
        follower.setSubscribers(new ArrayList<>());
        follower.setSubscriptions(new ArrayList<>());
        user.setFollower(follower);

        userRepository.save(user);

        return new SimpleResponseMessage("User registered successfully!");
    }

    @Override
    public UserProfileView getUserProfile(String username) {
        User user = userRepository.findByUsernameOrThrow(username);

        List<Post> posts = user.getPosts();
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        return UserProfileView.fromUser(user);
    }

    @Override
    public List<User> searchUsers(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return Collections.emptyList();
        }

        return userRepository.searchByUsernameOrFullName(searchTerm);
    }
}

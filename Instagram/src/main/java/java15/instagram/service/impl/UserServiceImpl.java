package java15.instagram.service.impl;

import java15.instagram.model.dto.request.UserRequest;
import java15.instagram.model.dto.response.SimpleResponseMessage;
import java15.instagram.model.entity.User;
import java15.instagram.model.enums.Role;
import java15.instagram.repository.UserRepository;
import java15.instagram.service.UserService;
import java15.instagram.service.validation.validator.UserRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        userRepository.save(user);

        return new SimpleResponseMessage("User registered successfully!");
    }
}

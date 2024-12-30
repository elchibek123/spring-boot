package java15.instagram.service.impl;

import java15.instagram.config.jwt.JwtService;
import java15.instagram.config.jwt.JwtToken;
import java15.instagram.exception.ValidationException;
import java15.instagram.model.dto.request.AuthRequest;
import java15.instagram.model.dto.response.LoginView;
import java15.instagram.model.entity.User;
import java15.instagram.repository.UserRepository;
import java15.instagram.service.AuthService;
import java15.instagram.service.mapper.LoginViewMapper;
import java15.instagram.service.validation.validator.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LoginViewMapper loginViewMapper;

    @Override
    public LoginView login(AuthRequest request) {
        User user = userRepository.findByUsernameOrThrow(request.username());

        boolean matches = passwordEncoder.matches(request.password(), user.getPassword());

        if (!matches) throw new ValidationException(ValidationExceptionType.INCORRECT_PASSWORD);

        JwtToken token = jwtService.createToken(user);

        return loginViewMapper.toView(user, token);
    }
}

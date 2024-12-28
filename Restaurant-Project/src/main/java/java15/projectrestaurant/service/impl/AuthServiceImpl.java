package java15.projectrestaurant.service.impl;

import jakarta.validation.ValidationException;
import java15.projectrestaurant.config.jwt.JwtService;
import java15.projectrestaurant.dto.request.AuthRequest;
import java15.projectrestaurant.dto.request.RegisterRequest;
import java15.projectrestaurant.dto.response.JwtToken;
import java15.projectrestaurant.dto.response.LoginView;
import java15.projectrestaurant.dto.response.SimpleResponseMessage;
import java15.projectrestaurant.enums.Role;
import java15.projectrestaurant.exception.ValidationExceptionType;
import java15.projectrestaurant.model.User;
import java15.projectrestaurant.repository.UserRepository;
import java15.projectrestaurant.service.AuthService;
import java15.projectrestaurant.validation.validator.UserRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRequestValidator userRequestValidator;

    @Override
    public LoginView login(AuthRequest request) {
        User user = userRepository.findByEmailOrThrow(request.email());
        boolean matches = passwordEncoder.matches(request.password(), user.getPassword());
        if (!matches) throw new ValidationException("Password is incorrect!");
        JwtToken token = jwtService.createToken(user);
        return LoginView.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    @Override
    public SimpleResponseMessage register(RegisterRequest request) {
        userRequestValidator.validate(request, RegisterRequest::email);

        try {
            User user = request.toEntity(new User(), passwordEncoder);

            user.setRole(Role.USER);

            userRepository.save(user);

            return new SimpleResponseMessage("User registered successfully!");
        } catch (ValidationException e) {
            throw new ValidationException(ValidationExceptionType.EMAIL_ALREADY_EXISTS);
        } catch (Exception e) {
            throw new RuntimeException("Error during user registration", e);
        }
    }
}

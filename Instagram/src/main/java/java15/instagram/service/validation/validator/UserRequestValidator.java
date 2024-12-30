package java15.instagram.service.validation.validator;

import jakarta.validation.ValidationException;
import java15.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserRequestValidator {
    private final UserRepository userRepository;

    public <T> void validate(T request, Function<T, String> emailExtractor, Function<T, String> usernameExtractor) {
        if (request == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }

        String email = emailExtractor.apply(request);
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ValidationException(ValidationExceptionType.EMAIL_ALREADY_EXISTS);
        }

        String username = usernameExtractor.apply(request);
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ValidationException(ValidationExceptionType.USERNAME_ALREADY_EXISTS);
        }
    }
}

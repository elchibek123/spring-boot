package java15.projectrestaurant.validation.validator;

import jakarta.validation.ValidationException;
import java15.projectrestaurant.exception.ValidationExceptionType;
import java15.projectrestaurant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserRequestValidator {
    private final UserRepository userRepository;

    public <T> void validate(T request, Function<T, String> emailExtractor) {
        if (request == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        String email = emailExtractor.apply(request);
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ValidationException(ValidationExceptionType.EMAIL_ALREADY_EXISTS);
        }
    }
}

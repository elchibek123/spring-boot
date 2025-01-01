package java15.projectrestaurant.validation.validator;

import jakarta.validation.ValidationException;
import java15.projectrestaurant.dto.request.RoleRequestDto;
import java15.projectrestaurant.enums.Role;
import java15.projectrestaurant.exception.ValidationExceptionType;
import java15.projectrestaurant.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class RoleRequestValidator {
    public void validate(RoleRequestDto request, User user) {
        if (request == null || user == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }

        if (request.role() == Role.CHEF) {
            int age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
            if (age < 25 || age > 45) {
                throw new ValidationException(ValidationExceptionType.CHEF_AGE);
            }

            if (request.experience() < 2) {
                throw new ValidationException(ValidationExceptionType.CHEF_EXPERIENCE);
            }
        }

        if (request.role() == Role.WAITER) {
            int age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
            if (age < 18 || age > 30) {
                throw new ValidationException(ValidationExceptionType.WAITER_AGE);
            }

            if (request.experience() < 1) {
                throw new ValidationException(ValidationExceptionType.WAITER_EXPERIENCE);
            }
        }
    }
}
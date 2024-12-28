package java15.projectrestaurant.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java15.projectrestaurant.validation.annotation.ValidPassword;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        return password.length() >= 4 && password.matches(".*\\d.*") && password.matches(".*[A-Z].*");
    }
}

package java15.instagram.service.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java15.instagram.service.validation.annotation.URLList;

import java.util.List;

public class UrlListValidator implements ConstraintValidator<URLList, List<String>> {

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        for (String url : value) {
            if (url == null || !url.matches("^(https?|ftp)://[^\s/$.?#].[^\s]*$")) {
                return false;
            }
        }
        return true;
    }
}

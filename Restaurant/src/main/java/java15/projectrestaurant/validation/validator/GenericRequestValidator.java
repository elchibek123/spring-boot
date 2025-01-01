package java15.projectrestaurant.validation.validator;

import java15.projectrestaurant.exception.BadRequestException;

public class GenericRequestValidator {
    public static <T, F> void validateField(T request, F field, String fieldName) {
        if (request == null || field == null) {
            throw new BadRequestException("Request or " + fieldName + " cannot be null.");
        }
    }
}
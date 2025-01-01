package java15.instagram.service.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java15.instagram.service.validation.validator.UrlListValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UrlListValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface URLList {

    String message() default "Must contain valid URLs";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

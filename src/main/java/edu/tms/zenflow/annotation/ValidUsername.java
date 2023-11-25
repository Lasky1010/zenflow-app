package edu.tms.zenflow.annotation;

import edu.tms.zenflow.validations.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UsernameValidator.class)
public @interface ValidUsername {
    String message() default "Username must be without spaces and has length from 3 to 20";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

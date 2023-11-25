package edu.tms.zenflow.annotation;

import edu.tms.zenflow.validations.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPass {
    String message() default "Password should have from 8 to 16 characters. At least one digit and Capital letter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

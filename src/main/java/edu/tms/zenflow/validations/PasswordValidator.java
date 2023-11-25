package edu.tms.zenflow.validations;

import edu.tms.zenflow.annotation.ValidPass;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPass, String> {

    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[A-Z])(?!.*\\s).{8,16}$";

    @Override
    public void initialize(ValidPass constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(PASSWORD_PATTERN);
    }
}

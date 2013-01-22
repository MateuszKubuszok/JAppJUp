package com.autoupdater.server.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.autoupdater.server.validators.PasswordConfirmationValidator;

/**
 * User's annotation used for validating password.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConfirmationValidator.class)
public @interface PasswordConfirmation {
    String message() default "{com.autoupdater.server.constraints.PasswordConfirmation.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

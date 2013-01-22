package com.autoupdater.server.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.autoupdater.server.validators.VersionNumberCorrectValidator;

/**
 * Update's annotation used for validating version number.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = VersionNumberCorrectValidator.class)
public @interface VersionNumberCorrect {
    String message() default "{com.autoupdater.server.constraints.VersionNumberCorrect.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

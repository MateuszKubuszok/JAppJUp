package com.autoupdater.server.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.autoupdater.server.validators.UniqueProgramNameValidator;

/**
 * Program's annotation used for ensuring uniqueness of a program's name.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniqueProgramNameValidator.class)
public @interface UniqueProgramName {
    String message() default "{com.autoupdater.server.constraints.UniqueProgramName.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

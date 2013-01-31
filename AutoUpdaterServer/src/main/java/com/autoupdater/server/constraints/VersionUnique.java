package com.autoupdater.server.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.autoupdater.server.validators.VersionUniqueValidator;

/**
 * Update's annotation used for validating version number.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = VersionUniqueValidator.class)
public @interface VersionUnique {
    String message() default "{com.autoupdater.server.constraints.VersionUnique.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

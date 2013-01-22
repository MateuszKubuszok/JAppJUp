package com.autoupdater.server.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.autoupdater.server.validators.FileAttatchedValidator;

/**
 * Updates' annotation used for validating presence of file.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FileAttatchedValidator.class)
public @interface FileAttatched {
    String message() default "{com.autoupdater.server.constraints.FileAttatched.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

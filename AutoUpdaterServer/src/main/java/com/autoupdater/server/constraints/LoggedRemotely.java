package com.autoupdater.server.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.autoupdater.server.validators.LoggedRemotelyValidator;

/**
 * Simulates remote logging of User.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = LoggedRemotelyValidator.class)
public @interface LoggedRemotely {
    boolean admin() default false;

    boolean packageAdmin() default false;

    String message() default "{com.autoupdater.server.constraints.LoggedRemotely.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

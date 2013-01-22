package com.autoupdater.server.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.autoupdater.server.validators.UniquePackageNameValidator;

/**
 * Package's annotation used for ensuring uniqueness of a package's name.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniquePackageNameValidator.class)
public @interface UniquePackageName {
    String message() default "{com.autoupdater.server.constraints.UniquePackageName.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.autoupdater.server.constraints.VersionNumberCorrect;
import com.autoupdater.server.models.Update;

/**
 * Used for validating correctness of a version number.
 */
@Component
public class VersionNumberCorrectValidator implements
        ConstraintValidator<VersionNumberCorrect, Update> {
    @Override
    public void initialize(VersionNumberCorrect constraintAnnotation) {
    }

    @Override
    public boolean isValid(Update update, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("version").addConstraintViolation();

        return update.getMajor() != 0 || update.getMinor() != 0 || update.getRelease() != 0
                || update.getNightly() != 0;
    }

}

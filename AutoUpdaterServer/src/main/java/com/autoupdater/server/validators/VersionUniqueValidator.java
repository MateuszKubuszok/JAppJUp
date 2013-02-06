package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autoupdater.server.constraints.VersionUnique;
import com.autoupdater.server.models.Package;
import com.autoupdater.server.models.Update;
import com.autoupdater.server.services.UpdateService;

/**
 * Used for validating correctness of a version uniqueness.
 */
@Component
public class VersionUniqueValidator implements ConstraintValidator<VersionUnique, Update> {
    /**
     * UpdateService instance.
     */
    @Autowired
    private UpdateService updateService;

    @Override
    public void initialize(VersionUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(Update update, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("version").addConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("developmentVersion").addConstraintViolation();

        Package _package = update.getThePackage();
        if (_package == null || update.getId() != 0)
            return true;

        return updateService.checkIfVersionAvailableForPackage(_package, update);
    }
}

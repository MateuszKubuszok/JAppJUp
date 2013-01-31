package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autoupdater.server.constraints.VersionUnique;
import com.autoupdater.server.models.Package;
import com.autoupdater.server.models.Update;
import com.autoupdater.server.services.PackageService;

/**
 * Used for validating correctness of a version uniqueness.
 */
@Component
public class VersionUniqueValidator implements ConstraintValidator<VersionUnique, Update> {
    /**
     * PackageService instance.
     */
    @Autowired
    private PackageService packageService;

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
        if (_package == null)
            return true;

        for (Update checkedUpdate : _package.getUpdates())
            if (updatesVersionIsOccupied(update, checkedUpdate))
                return false;

        return true;
    }

    /**
     * Whether updates occupy the same version number and version type.
     * 
     * @param update1
     *            some update
     * @param update2
     *            some other update
     * @return true if both are/aren't development version
     */
    private boolean updatesVersionIsOccupied(Update update1, Update update2) {
        return update1 != null && update2 != null
                && update1.isDevelopmentVersion() == update2.isDevelopmentVersion()
                && update1.getVersion().equals(update2.getVersion());
    }
}

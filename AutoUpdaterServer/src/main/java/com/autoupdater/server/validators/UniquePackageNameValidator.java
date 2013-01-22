package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autoupdater.server.constraints.UniquePackageName;
import com.autoupdater.server.models.Package;
import com.autoupdater.server.services.PackageService;
import com.autoupdater.server.services.ProgramService;

/**
 * Validates uniqueness of a program's name.
 */
@Component
public class UniquePackageNameValidator implements ConstraintValidator<UniquePackageName, Package> {
    @Autowired
    private ProgramService programService;

    @Autowired
    private PackageService packageService;

    @Override
    public void initialize(UniquePackageName constraintAnnotation) {
    }

    @Override
    public boolean isValid(Package _package, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("name").addConstraintViolation();

        if (_package.getProgram() == null || _package.getName() == null
                || _package.getName().isEmpty())
            return true;

        programService.refresh(_package.getProgram());
        Package anotherPackage = _package.getProgram().getPackageWithName(_package.getName());

        return anotherPackage == null || anotherPackage.getId() == _package.getId();
    }
}

package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autoupdater.server.commands.RemoteUpdateUpload;
import com.autoupdater.server.constraints.ProgramAndPackageExist;
import com.autoupdater.server.models.Program;
import com.autoupdater.server.services.ProgramService;

/**
 * Validates program and package names.
 */
@Component
public class ProgramAndPackageExistValidator implements
        ConstraintValidator<ProgramAndPackageExist, RemoteUpdateUpload> {
    /**
     * ProgramService instance.
     */
    @Autowired
    private ProgramService programService;

    @Override
    public void initialize(ProgramAndPackageExist constraintAnnotation) {
    }

    @Override
    public boolean isValid(RemoteUpdateUpload remoteUpdateUpload, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("program").addConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("thePackage").addConstraintViolation();

        Program program = programService.findByName(remoteUpdateUpload.getProgram());

        if (program != null && program.containsPackageWithName(remoteUpdateUpload.getThePackage())) {
            setupPackage(remoteUpdateUpload, program);
            return true;
        }

        return false;
    }

    /**
     * Sets up Package inside Update.
     * 
     * @param remoteUpdateUpload
     *            command object
     * @param program
     *            program with required package
     */
    private void setupPackage(RemoteUpdateUpload remoteUpdateUpload, Program program) {
        remoteUpdateUpload.getUpdate().setThePackage(
                program.getPackageWithName(remoteUpdateUpload.getThePackage()));
    }
}

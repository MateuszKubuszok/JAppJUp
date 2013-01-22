package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autoupdater.server.constraints.UniqueProgramName;
import com.autoupdater.server.models.Program;
import com.autoupdater.server.services.ProgramService;

/**
 * Validates uniqueness of a package's name.
 */
@Component
public class UniqueProgramNameValidator implements ConstraintValidator<UniqueProgramName, Program> {
    /**
     * ProgramService instance.
     */
    @Autowired
    private ProgramService programService;

    @Override
    public void initialize(UniqueProgramName constraintAnnotation) {
    }

    @Override
    public boolean isValid(Program program, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("name").addConstraintViolation();

        if (program.getName() == null || program.getName().isEmpty())
            return true;

        Program anotherProgram = programService.findByName(program.getName());

        return anotherProgram == null || anotherProgram.getName() == null
                || anotherProgram.getId() == program.getId();
    }
}

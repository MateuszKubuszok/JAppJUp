package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.autoupdater.server.constraints.UpdaterCommandDefined;
import com.autoupdater.server.models.EUpdateStrategy;
import com.autoupdater.server.models.Update;

/**
 * Validates confirmation of a password during password change.
 */
@Component
public class UpdaterCommandDefinedValidator implements
        ConstraintValidator<UpdaterCommandDefined, Update> {
    @Override
    public void initialize(UpdaterCommandDefined constraintAnnotation) {
    }

    @Override
    public boolean isValid(Update update, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("updaterCommand").addConstraintViolation();

        if (EUpdateStrategy.EXECUTE.equals(update.getType()))
            return update.getUpdaterCommand() != null && !update.getUpdaterCommand().isEmpty();
        return true;
    }
}

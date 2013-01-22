package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.autoupdater.server.commands.PasswordCommandInterface;
import com.autoupdater.server.constraints.PasswordConfirmation;

/**
 * Validates confirmation of a password during password change.
 */
@Component
public class PasswordConfirmationValidator implements
        ConstraintValidator<PasswordConfirmation, PasswordCommandInterface> {
    @Override
    public void initialize(PasswordConfirmation constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswordCommandInterface passwordCommand,
            ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("confirmPassword").addConstraintViolation();

        if (passwordCommand.getConfirmPassword() != null
                && !passwordCommand.getConfirmPassword().isEmpty())
            return passwordCommand.getConfirmPassword().equals(passwordCommand.getPassword());
        return true;
    }
}

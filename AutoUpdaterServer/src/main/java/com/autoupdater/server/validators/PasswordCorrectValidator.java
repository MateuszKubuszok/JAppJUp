package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.autoupdater.server.commands.PasswordEditionCommand;
import com.autoupdater.server.constraints.PasswordCorrect;
import com.autoupdater.server.models.User;
import com.autoupdater.server.services.UserService;

/**
 * Used for validating correctness of an old password.
 */
@Component
public class PasswordCorrectValidator implements
        ConstraintValidator<PasswordCorrect, PasswordEditionCommand> {
    /**
     * UserService instance.
     */
    @Autowired
    private UserService userService;

    @Override
    public void initialize(PasswordCorrect constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswordEditionCommand passwordEditionCommand,
            ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("currentPassword").addConstraintViolation();

        User user = userService.findById(passwordEditionCommand.getUserId());

        if (user == null)
            return false;
        return BCrypt
                .checkpw(passwordEditionCommand.getCurrentPassword(), user.getHashedPassword());
    }

}

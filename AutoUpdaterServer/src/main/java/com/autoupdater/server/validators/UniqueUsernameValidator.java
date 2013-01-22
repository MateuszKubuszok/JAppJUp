package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autoupdater.server.constraints.UniqueUsername;
import com.autoupdater.server.models.User;
import com.autoupdater.server.services.UserService;

/**
 * Validates uniqueness of a username.
 */
@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, User> {
    /**
     * UserService instance.
     */
    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("username").addConstraintViolation();

        if (user.getUsername() == null || user.getUsername().isEmpty())
            return true;

        User anotherUser = userService.findByUsername(user.getUsername());

        return anotherUser == null || anotherUser.getUsername() == null
                || anotherUser.getId() == user.getId();
    }
}

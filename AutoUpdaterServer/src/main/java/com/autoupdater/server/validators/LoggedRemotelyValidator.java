package com.autoupdater.server.validators;

import static org.springframework.security.crypto.bcrypt.BCrypt.checkpw;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autoupdater.server.commands.RemoteUpdateUpload;
import com.autoupdater.server.constraints.LoggedRemotely;
import com.autoupdater.server.models.User;
import com.autoupdater.server.services.UserService;

/**
 * Credentials of remotely logged user.
 */
@Component
public class LoggedRemotelyValidator implements
        ConstraintValidator<LoggedRemotely, RemoteUpdateUpload> {
    /**
     * UserService instance.
     */
    @Autowired
    private UserService userService;

    private LoggedRemotely annotation;

    @Override
    public void initialize(LoggedRemotely constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(RemoteUpdateUpload remoteUpdateUpload, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("username").addConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("password").addConstraintViolation();

        User user = userService.findByUsername(remoteUpdateUpload.getUsername());

        if (user != null && checkpw(remoteUpdateUpload.getPassword(), user.getHashedPassword())
                && (!annotation.admin() || user.isAdmin())
                && (!annotation.packageAdmin() || user.isPackageAdmin())) {
            remoteUpdateUpload.getUpdate().setUploader(user);
            return true;
        }
        return false;
    }
}

/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
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

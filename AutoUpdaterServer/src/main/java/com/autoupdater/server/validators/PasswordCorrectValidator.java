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
        return checkpw(passwordEditionCommand.getCurrentPassword(), user.getHashedPassword());
    }
}

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

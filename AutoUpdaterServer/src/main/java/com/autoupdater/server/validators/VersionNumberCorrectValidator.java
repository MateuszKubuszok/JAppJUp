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

import org.springframework.stereotype.Component;

import com.autoupdater.server.constraints.VersionNumberCorrect;
import com.autoupdater.server.models.Update;

/**
 * Used for validating correctness of a version number.
 */
@Component
public class VersionNumberCorrectValidator implements
        ConstraintValidator<VersionNumberCorrect, Update> {
    @Override
    public void initialize(VersionNumberCorrect constraintAnnotation) {
    }

    @Override
    public boolean isValid(Update update, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("version").addConstraintViolation();

        return update.getMajor() != 0 || update.getMinor() != 0 || update.getRelease() != 0
                || update.getNightly() != 0;
    }

}

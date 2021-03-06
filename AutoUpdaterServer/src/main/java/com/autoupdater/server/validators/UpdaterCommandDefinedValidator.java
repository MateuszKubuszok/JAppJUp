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

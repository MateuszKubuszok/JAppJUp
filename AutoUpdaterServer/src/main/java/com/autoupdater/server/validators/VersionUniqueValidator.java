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

import com.autoupdater.server.constraints.VersionUnique;
import com.autoupdater.server.models.Package;
import com.autoupdater.server.models.Update;
import com.autoupdater.server.services.UpdateService;

/**
 * Used for validating correctness of a version uniqueness.
 */
@Component
public class VersionUniqueValidator implements ConstraintValidator<VersionUnique, Update> {
    /**
     * UpdateService instance.
     */
    @Autowired
    private UpdateService updateService;

    @Override
    public void initialize(VersionUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(Update update, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("version").addConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("developmentVersion").addConstraintViolation();

        Package _package = update.getThePackage();
        if (_package == null || update.getId() != 0)
            return true;

        return updateService.checkIfVersionAvailableForPackage(_package, update);
    }
}

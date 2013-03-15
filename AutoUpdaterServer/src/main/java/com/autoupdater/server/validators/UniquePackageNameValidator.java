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

import com.autoupdater.server.constraints.UniquePackageName;
import com.autoupdater.server.models.Package;
import com.autoupdater.server.services.PackageService;
import com.autoupdater.server.services.ProgramService;

/**
 * Validates uniqueness of a program's name.
 */
@Component
public class UniquePackageNameValidator implements ConstraintValidator<UniquePackageName, Package> {
    @Autowired
    private ProgramService programService;

    @Autowired
    private PackageService packageService;

    @Override
    public void initialize(UniquePackageName constraintAnnotation) {
    }

    @Override
    public boolean isValid(Package _package, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("name").addConstraintViolation();

        if (_package.getProgram() == null || _package.getName() == null
                || _package.getName().isEmpty())
            return true;

        programService.refresh(_package.getProgram());
        Package anotherPackage = _package.getProgram().getPackageWithName(_package.getName());

        return anotherPackage == null || anotherPackage.getId() == _package.getId();
    }
}

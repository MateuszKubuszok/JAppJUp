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

import com.autoupdater.server.constraints.UniqueProgramName;
import com.autoupdater.server.models.Program;
import com.autoupdater.server.services.ProgramService;

/**
 * Validates uniqueness of a package's name.
 */
@Component
public class UniqueProgramNameValidator implements ConstraintValidator<UniqueProgramName, Program> {
    /**
     * ProgramService instance.
     */
    @Autowired
    private ProgramService programService;

    @Override
    public void initialize(UniqueProgramName constraintAnnotation) {
    }

    @Override
    public boolean isValid(Program program, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("name").addConstraintViolation();

        if (program.getName() == null || program.getName().isEmpty())
            return true;

        Program anotherProgram = programService.findByName(program.getName());

        return anotherProgram == null || anotherProgram.getName() == null
                || anotherProgram.getId() == program.getId();
    }
}

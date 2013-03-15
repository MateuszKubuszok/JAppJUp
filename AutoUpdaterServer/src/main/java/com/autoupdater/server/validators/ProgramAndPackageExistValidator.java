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

import com.autoupdater.server.commands.RemoteUpdateUpload;
import com.autoupdater.server.constraints.ProgramAndPackageExist;
import com.autoupdater.server.models.Program;
import com.autoupdater.server.services.ProgramService;

/**
 * Validates program and package names.
 */
@Component
public class ProgramAndPackageExistValidator implements
        ConstraintValidator<ProgramAndPackageExist, RemoteUpdateUpload> {
    /**
     * ProgramService instance.
     */
    @Autowired
    private ProgramService programService;

    @Override
    public void initialize(ProgramAndPackageExist constraintAnnotation) {
    }

    @Override
    public boolean isValid(RemoteUpdateUpload remoteUpdateUpload, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("program").addConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("thePackage").addConstraintViolation();

        Program program = programService.findByName(remoteUpdateUpload.getProgram());

        if (program != null && program.containsPackageWithName(remoteUpdateUpload.getThePackage())) {
            setupPackage(remoteUpdateUpload, program);
            return true;
        }

        return false;
    }

    /**
     * Sets up Package inside Update.
     * 
     * @param remoteUpdateUpload
     *            command object
     * @param program
     *            program with required package
     */
    private void setupPackage(RemoteUpdateUpload remoteUpdateUpload, Program program) {
        remoteUpdateUpload.getUpdate().setThePackage(
                program.getPackageWithName(remoteUpdateUpload.getThePackage()));
    }
}

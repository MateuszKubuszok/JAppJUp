package com.autoupdater.server.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.autoupdater.server.constraints.FileAttatched;
import com.autoupdater.server.models.Update;

/**
 * Validates presence of file - either as newly uploaded file OR blob.
 */
@Component
public class FileAttatchedValidator implements ConstraintValidator<FileAttatched, Update> {
    @Override
    public void initialize(FileAttatched constraintAnnotation) {
    }

    @Override
    public boolean isValid(Update update, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("file").addConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode("fileData").addConstraintViolation();

        return (update.getFile() != null && !update.getFile().isEmpty() || update.getFileData() != null
                && !update.getFileData().isEmpty());
    }
}

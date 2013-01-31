package com.autoupdater.server.controllers;

import static org.apache.log4j.Logger.getLogger;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.autoupdater.server.services.BugService;
import com.autoupdater.server.services.FileService;
import com.autoupdater.server.services.PackageService;
import com.autoupdater.server.services.ProgramService;
import com.autoupdater.server.services.UpdateService;
import com.autoupdater.server.services.UserService;

/**
 * Parent of all controllers used in project.
 * 
 * Autowires services so that that can be used in a controller on the fly.
 */
public abstract class AppController {
    /**
     * UserService instance.
     */
    @Autowired
    protected UserService userService;

    /**
     * ProgramService instance.
     */
    @Autowired
    protected ProgramService programService;

    /**
     * PackageService instance.
     */
    @Autowired
    protected PackageService packageService;

    /**
     * PackageService instance.
     */
    @Autowired
    protected UpdateService updateService;

    /**
     * PackageService instance.
     */
    @Autowired
    protected BugService bugService;

    /**
     * FileService instance.
     */
    @Autowired
    protected FileService fileService;

    /**
     * AppController's logger.
     */
    private static final Logger logger = getLogger(AppController.class);

    /**
     * Sends error instead of displaying page.
     * 
     * @param response
     *            response instance
     * @param errorCode
     *            error code from HttpServletResponse
     * @param message
     *            message to display
     */
    protected void sendError(HttpServletResponse response, int errorCode, String message) {
        try {
            response.sendError(errorCode, message);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}

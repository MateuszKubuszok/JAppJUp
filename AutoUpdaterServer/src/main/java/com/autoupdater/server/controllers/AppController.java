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
package com.autoupdater.server.controllers;

import static org.apache.log4j.Logger.getLogger;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.autoupdater.server.models.User;
import com.autoupdater.server.services.BugService;
import com.autoupdater.server.services.FileService;
import com.autoupdater.server.services.PackageService;
import com.autoupdater.server.services.ProgramService;
import com.autoupdater.server.services.UpdateService;
import com.autoupdater.server.services.UserService;
import com.autoupdater.server.utils.authentication.CurrentUserUtil;

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
     * Sets up current user at beginning of each request.
     * 
     * @return current user
     */
    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        User currentUser = userService.findByUsername(CurrentUserUtil.getUsername());
        logger.debug("Sets up current user: " + currentUser);
        return currentUser;
    }

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

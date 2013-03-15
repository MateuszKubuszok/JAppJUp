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
import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.autoupdater.server.commands.PasswordEditionCommand;
import com.autoupdater.server.models.User;
import com.autoupdater.server.utils.authentication.CurrentUserUtil;

/**
 * Responsible for rendering sign in form.
 */
@Controller
@SessionAttributes({ "passwordEditionCommand", "user" })
public final class SignInController extends AppController {
    /**
     * Controller's logger.
     */
    private static final Logger logger = getLogger(SignInController.class);

    /**
     * Renders sign in form.
     * 
     * Let JSF run /views/signIn/SignIn.jsp on GET /server/sign_in request.
     * 
     * @return facelet name
     */
    @RequestMapping("/sign_in")
    public String SignInInit() {
        logger.debug("Received request: GET /sign_in");
        logger.debug("Renders request: GET /sign_in");
        return "signIn/signIn";
    }

    /**
     * Renders change-password form.
     * 
     * Let JSF run /views/signIn/changePassword.jsp on GET /server/changepw
     * request.
     * 
     * @param model
     *            passed user model
     * @return facelet name
     */
    @RequestMapping(value = "/changepw", method = GET)
    public String editPasswordForm(Model model) {
        logger.debug("Received request: GET /changepw");

        User user = userService.findByUsername(CurrentUserUtil.getUsername());

        PasswordEditionCommand passwordEditionCommand = new PasswordEditionCommand();
        passwordEditionCommand.setUserId(user.getId());

        model.addAttribute("passwordEditionCommand", passwordEditionCommand);
        model.addAttribute("user", user);

        logger.debug("Renders request: GET /changepw");
        return "signIn/changePassword";
    }

    /**
     * Handles change-password request.
     * 
     * Tries to change password on POST /server/changepw request.
     * 
     * @param passwordEditionCommand
     *            PasswordEdit model
     * @param result
     *            response that will be sent
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/changepw", method = POST)
    public String editPassword(
            @Valid @ModelAttribute("passwordEditionCommand") PasswordEditionCommand passwordEditionCommand,
            BindingResult result, @ModelAttribute("user") User user, Model model) {
        logger.debug("Received request: POST /changepw");

        if (result.hasErrors()) {
            model.addAttribute("passwordEditionCommand", passwordEditionCommand);

            logger.debug("Renders request: POST /changepw (validation failed)");
            return "signIn/changePassword";
        }

        user.setPassword(passwordEditionCommand.getPassword());
        userService.merge(user);

        logger.debug("Renders request: POST /changepw (password changed)");
        return "signIn/passwordChanged";
    }

    /**
     * Filters values passed to command object "passwordEditionCommand".
     * 
     * @param binder
     *            binder that will bind sent information to command object
     */
    @InitBinder("passwordEditionCommand")
    public void configureBinfingOfPasswordEditionCommand(WebDataBinder binder) {
        logger.debug("Securing \"passwordEditionCommand\" modelAttribute");
        binder.setAllowedFields("currentPassword", "password", "confirmPassword", "userId");
    }

    /**
     * Disallow changes of session attribute "user".
     * 
     * @param binder
     *            binder that will bind sent information to command object
     */
    @InitBinder("user")
    public void configureBinfingOfUser(WebDataBinder binder) {
        logger.debug("Securing \"user\" modelAttribute");
        binder.setAllowedFields();
    }
}

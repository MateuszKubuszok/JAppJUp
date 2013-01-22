package com.autoupdater.server.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private static final Logger logger = LoggerFactory.getLogger(SignInController.class);

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
    @RequestMapping(value = "/changepw", method = RequestMethod.GET)
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
    @RequestMapping(value = "/changepw", method = RequestMethod.POST)
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

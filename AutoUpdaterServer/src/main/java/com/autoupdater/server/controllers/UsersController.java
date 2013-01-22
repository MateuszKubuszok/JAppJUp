package com.autoupdater.server.controllers;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.autoupdater.server.models.EUserType;
import com.autoupdater.server.models.User;

/**
 * Responsible for rendering user panel.
 */
@Controller
@SessionAttributes({ "newUser", "user" })
@RequestMapping(value = "/users")
public final class UsersController extends AppController {
    /**
     * Controller's logger.
     */
    private static Logger logger = Logger.getLogger(UsersController.class);

    /**
     * Renders list of users.
     * 
     * Let JSF run /views/users/index.jsp on GET /server/users request.
     * 
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        logger.debug("Received request: GET /users");

        model.addAttribute("users", userService.findAll());

        logger.debug("Renders requiest: /users");
        return "users/index";
    }

    /**
     * Renders new users form.
     * 
     * Let JSF run /views/users/new.jsp on GET /server/users/add request.
     * 
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createForm(Model model) {
        logger.debug("Received request: GET /users/add");

        model.addAttribute("newUser", new User());
        model.addAttribute("types", EUserType.values());

        logger.debug("Renders request: GET /users/add");
        return "users/new";
    }

    /**
     * Saves new user.
     * 
     * Creates user and redirects to /users on POST /server/users/add request.
     * 
     * On errors let JSF run /views/users/new.jsp.
     * 
     * @param user
     *            user
     * @param result
     *            validation result
     * @param model
     *            passed model
     * @return facelet name or redirect
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("newUser") User user, BindingResult result,
            Model model) {
        logger.debug("Received request: POST /users/add");

        if (result.hasErrors()) {
            model.addAttribute("newUser", user);

            logger.debug("Renders request: POST /users/add (validation failed)");
            return "users/new";
        }

        userService.persist(user);

        logger.debug("Redirect to /users (User created)");
        return "redirect:";
    }

    /**
     * Renders form to edit existing user.
     * 
     * Let JSF run /views/users/edit.jsp on GET /server/updates/edit/{id}
     * request.
     * 
     * @param id
     *            user's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("Received request: GET /users/edit/" + id);

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("types", EUserType.values());

        logger.debug("Renders request: GET /users/edit/" + id);
        return "users/edit";
    }

    /**
     * Saves changes.
     * 
     * Updates user and redirects to /users on POST /server/users/edit request.
     * 
     * On errors let JSF run /views/users/edit.jsp.
     * 
     * @param user
     *            user to be changed
     * @param result
     *            validation result
     * @param model
     *            passed model
     * @return facelet name or redirect
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        logger.debug("Received request: POST /users/edit");

        if (result.hasErrors()) {
            model.addAttribute("user", user);

            logger.debug("Renders request: POST /users/edit (validation failed)");
            return "users/edit";
        }

        userService.merge(user);

        logger.debug("Redirect to /users (User updated)");
        return "redirect:";
    }

    /**
     * Deletes user from list.
     * 
     * Deletes user and redirects to /users on GET /server/users/delete/{id}
     * request.
     * 
     * @param id
     *            user's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Integer id, Model model) {
        logger.debug("Received request: GET /users/delete/" + id);

        userService.remove(userService.findById(id));

        logger.debug("Redirect to /users (User deleted)");
        return "redirect:";
    }

    /**
     * Filters values passed to command object "newUser".
     * 
     * @param binder
     *            binder that will bind sent information to command object
     */
    @InitBinder("newUser")
    public void configureBindingOfNewUser(WebDataBinder binder) {
        logger.debug("Securing \"newUser\" modelAttribute");
        binder.setAllowedFields("username", "name", "password", "confirmPassword", "userType");
    }

    /**
     * Filters values passed to command object "user".
     * 
     * @param binder
     *            binder that will bind sent information to command object
     */
    @InitBinder("user")
    public void configureBindingOfUser(WebDataBinder binder) {
        logger.debug("Securing \"user\" modelAttribute");
        binder.setAllowedFields("username", "name", "userType");
    }
}
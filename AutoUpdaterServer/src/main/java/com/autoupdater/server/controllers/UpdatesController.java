package com.autoupdater.server.controllers;

import java.io.IOException;

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

import com.autoupdater.server.models.EUpdateStrategy;
import com.autoupdater.server.models.Package;
import com.autoupdater.server.models.Update;
import com.autoupdater.server.utils.authentication.CurrentUserUtil;

/**
 * Responsible for rendering updates panel.
 * 
 * @FIXME repair saving file to database
 */
@Controller
@RequestMapping(value = "/updates")
@SessionAttributes({ "newUpdate", "update" })
public final class UpdatesController extends AppController {
    /**
     * Controller's logger.
     */
    private static Logger logger = Logger.getLogger(UpdatesController.class);

    /**
     * Renders list of packages.
     * 
     * Let JSF run /views/updates/index.jsp on GET /server/updates/{packageID}
     * request.
     * 
     * @param packageID
     *            package's ID
     * @param model
     *            passed updates model
     * @return facelet name
     */
    @RequestMapping(value = "/{packageID}", method = RequestMethod.GET)
    public String index(@PathVariable("packageID") int packageID, Model model) {
        logger.debug("Received request: GET /updates/" + packageID);

        Package _package = packageService.findById(packageID);

        model.addAttribute("user", userService.findByUsername(CurrentUserUtil.getUsername()));
        model.addAttribute("thePackage", _package);
        model.addAttribute("updates", _package.getUpdates());

        logger.debug("Renders request: GET /updates/" + packageID);
        return "updates/index";
    }

    /**
     * Renders new update form.
     * 
     * Let JSF run /views/updates/new.jsp on GET /server/updates/add/{packageID}
     * request.
     * 
     * @param packageID
     *            package's ID
     * @param model
     *            passed update model
     * @return facelet name
     */
    @RequestMapping(value = "/add/{packageID}", method = RequestMethod.GET)
    public String createForm(@PathVariable("packageID") int packageID, Model model) {
        logger.debug("Received request: GET /updates/add/" + packageID);

        Update update = new Update();
        update.setThePackage(packageService.findById(packageID));
        update.setUploader(userService.findByUsername(CurrentUserUtil.getUsername()));

        model.addAttribute("newUpdate", update);
        model.addAttribute("updateTypes", EUpdateStrategy.values());

        logger.debug("Renders request: GET /updates/add/" + packageID);
        return "updates/new";
    }

    /**
     * Saves new update.
     * 
     * Creates update and redirects to /updates/{packageID} on POST
     * /server/updates/add request.
     * 
     * On errors let JSF run /views/packages/new.jsp.
     * 
     * @param update
     *            update to be created
     * @param result
     *            validation result
     * @param model
     *            passed model
     * @return facelet name or redirect
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute(value = "newUpdate") Update update,
            BindingResult result, Model model) {
        logger.debug("Received request: POST /updates/add");

        if (result.hasErrors()) {
            model.addAttribute("newUpdate", update);
            model.addAttribute("updateTypes", EUpdateStrategy.values());

            logger.debug("Renders request: POST /updates/add (validation failed)");
            return "updates/new";
        }

        try {
            updateService.persist(update);
        } catch (IOException e) {
            model.addAttribute("newUpdate", update);
            model.addAttribute("updateTypes", EUpdateStrategy.values());

            logger.error("Renders request: POST /updates/add (file save failed)");
            return "updates/new";
        }

        logger.debug("Redirect to /updates/" + update.getThePackage().getId() + " (Update created)");
        return "redirect:" + update.getThePackage().getId();
    }

    /**
     * Renders form to edit existing package.
     * 
     * Let JSF run /views/updates/edit.jsp on GET /server/updates/edit/{id}
     * request.
     * 
     * @param id
     *            updates's ID
     * @param model
     *            passed updates model
     * @return facelet name
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") int id, Model model) {
        logger.debug("Received request: GET /updates/edit/" + id);

        model.addAttribute("update", updateService.findById(id));
        model.addAttribute("updateTypes", EUpdateStrategy.values());

        logger.debug("Renders request: GET /updates/edit/" + id);
        return "updates/edit";
    }

    /**
     * Saves changes.
     * 
     * Let JSF run /views/updates/edit.jsp on POST /server/packages/edit/[id]
     * request.
     * 
     * On errors let JSF run /views/updates/edit.jsp.
     * 
     * @param update
     *            update
     * @param result
     *            validation result
     * @param model
     *            passed model
     * @return facelet name or redirect
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute(value = "update") Update update,
            BindingResult result, Model model) {
        logger.debug("Received request: POST /updates/edit");

        if (result.hasErrors()) {
            model.addAttribute("update", update);
            model.addAttribute("updateTypes", EUpdateStrategy.values());

            logger.debug("Renders request: POST /updates/edit (validation failed)");
            return "updates/edit";
        }

        updateService.merge(update);

        logger.debug("Redirect to /updates/" + update.getThePackage().getId() + " (Update updated)");
        return "redirect:" + update.getThePackage().getId();
    }

    /**
     * Deletes update.
     * 
     * @param id
     *            update's ID
     * @param model
     *            model
     * @return facelet name
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id, Model model) {
        logger.debug("Received request: GET /updates/delete/" + id);

        Update update = updateService.findById(id);
        int packageID = update.getThePackage().getId();
        updateService.remove(update);

        logger.debug("Redirect to /updates/" + update.getThePackage().getId() + " (Update deleted)");
        return "redirect:./../" + packageID;
    }

    /**
     * Filters values passed to command object "newUpdate".
     * 
     * @param binder
     *            binder that will bind sent information to command object
     */
    @InitBinder("newUpdate")
    public void configureBinderOfNewUpdates(WebDataBinder binder) {
        logger.debug("Securing \"newUpdate\" modelAttribute");
        binder.setAllowedFields("version", "developmentVersion", "changelog", "type",
                "relativePath", "updaterCommand", "file");
    }

    /**
     * Filters values passed to command object "update".
     * 
     * @param binder
     *            binder that will bind sent information to command object
     */
    @InitBinder("update")
    public void configureBinderOfUpdates(WebDataBinder binder) {
        logger.debug("Securing \"update\" modelAttribute");
        binder.setAllowedFields("version", "developmentVersion", "changelog", "type",
                "relativePath", "updaterCommand");
    }
}

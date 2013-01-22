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

import com.autoupdater.server.models.Package;
import com.autoupdater.server.models.Program;
import com.autoupdater.server.utils.authentication.CurrentUserUtil;

/**
 * Responsible for handling package panel.
 */
@Controller
@SessionAttributes({ "_package" })
@RequestMapping(value = "/packages")
public final class PackagesController extends AppController {
    /**
     * Controller's logger.
     */
    private static Logger logger = Logger.getLogger(PackagesController.class);

    /**
     * Renders list of packages.
     * 
     * Let JSF run /views/packages/index.jsp on GET /server/packages/{programID}
     * request.
     * 
     * @param programID
     *            program's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String index(@PathVariable("id") int programID, Model model) {
        logger.debug("Received request: GET /packages/" + programID);

        Program program = programService.findById(programID);

        model.addAttribute("user", userService.findByUsername(CurrentUserUtil.getUsername()));
        model.addAttribute("packages",
                program != null ? program.getPackages() : packageService.findAll());
        model.addAttribute("program", program);

        logger.debug("Renders request: GET /packages/" + programID);
        return "packages/index";
    }

    /**
     * Renders new package form.
     * 
     * Let JSF run /views/packages/new.jsp on GET
     * /server/packages/add/{programID} request.
     * 
     * @param programID
     *            program's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    public String createForm(@PathVariable("id") int programID, Model model) {
        logger.debug("Received request: GET /programs/add/" + programID);

        Package _package = new Package();
        _package.setProgram(programService.findById(programID));

        model.addAttribute("_package", _package);

        logger.debug("Renders request: GET /packages/add/" + programID);
        return "packages/new";
    }

    /**
     * Saves new package.
     * 
     * Creates package and redirects to /packages/{programID} on POST
     * /server/packages/add request.
     * 
     * On errors let JSF run /views/packages/new.jsp.
     * 
     * @param _package
     *            package
     * @param result
     *            validation result
     * @param model
     *            passed model
     * @return facelet name or redirect
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute(value = "_package") Package _package,
            BindingResult result, Model model) {
        logger.debug("Received request: POST /packages/add");

        if (result.hasErrors()) {
            model.addAttribute("_package", _package);

            logger.debug("Renders request: POST /packages/add (validation failed)");
            return "packages/new";
        }

        packageService.persist(_package);

        logger.debug("Redirect to /packages/" + _package.getProgram().getId()
                + " (Package created)");
        return "redirect:" + _package.getProgram().getId();
    }

    /**
     * Renders form to edit existing package.
     * 
     * Let JSF run /views/packages/edit.jsp on GET /server/packages/edit/{id}
     * request.
     * 
     * @param id
     *            package's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") int id, Model model) {
        logger.debug("Received request: GET /packages/edit/" + id);

        model.addAttribute("_package", packageService.findById(id));

        logger.debug("Renders request: GET /packages/edit/" + id);
        return "packages/edit";
    }

    /**
     * Saves changes.
     * 
     * Updates program and redirects to /packages/{programID} on POST
     * /server/packages/edit request.
     * 
     * On errors let JSF run /views/packages/edit.jsp.
     * 
     * @param _package
     *            package
     * @param id
     *            package's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("_package") Package _package, BindingResult result,
            Model model) {
        logger.debug("Received request: POST /programs/edit");

        if (result.hasErrors()) {
            model.addAttribute("_package", _package);

            logger.debug("Renders request: POST /packages/edit (validation failed)");
            return "packages/edit";
        }

        packageService.merge(_package);

        logger.debug("Redirect to /packages/" + _package.getProgram().getId()
                + " (Package updated)");
        return "redirect:" + _package.getProgram().getId();
    }

    /**
     * Deletes program.
     * 
     * Deletes program and redirects to /packages on GET
     * /server/packages/delete/{id} request.
     * 
     * @param id
     *            package's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id, Model model) {
        logger.debug("Received request: GET /packages/delete/" + id);

        Package _package = packageService.findById(id);
        int programID = _package.getProgram().getId();
        packageService.remove(_package);

        logger.debug("Redirect to /packages/" + programID + " (Package deleted)");
        return "redirect:./../" + programID;
    }

    /**
     * Filters values passed to command object "_package".
     * 
     * @param binder
     *            binder that will bind sent information to command object
     */
    @InitBinder("_package")
    public void configureBindingOfPackage(WebDataBinder binder) {
        logger.debug("Securing \"_package\" modelAttribute");
        binder.setAllowedFields("name");
    }
}

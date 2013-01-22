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

import com.autoupdater.server.models.Bug;
import com.autoupdater.server.models.Program;
import com.autoupdater.server.utils.authentication.CurrentUserUtil;

/**
 * Responsible for handling bugs panel.
 */
@Controller
@SessionAttributes({ "bug" })
@RequestMapping(value = "/bugs")
public final class BugsController extends AppController {
    /**
     * Controller's logger.
     */
    private static Logger logger = Logger.getLogger(BugsController.class);

    /**
     * Renders list of bugs.
     * 
     * Let JSF run /views/bugs/index.jsp on GET /server/bugs/{programID}
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
        logger.debug("Received request: GET /bugs/" + programID);

        Program program = programService.findById(programID);

        model.addAttribute("user", userService.findByUsername(CurrentUserUtil.getUsername()));
        model.addAttribute("bugs", program != null ? program.getBugs() : bugService.findAll());
        model.addAttribute("programID", programID);

        logger.debug("Renders request: GET /bugs/" + programID);
        return "bugs/index";
    }

    /**
     * Renders new bug form.
     * 
     * Let JSF run /views/bug/new.jsp on GET /server/bugs/add/{programID}
     * request.
     * 
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    public String createForm(@PathVariable("id") int programID, Model model) {
        logger.debug("Received request: GET /bugs/add/" + programID);

        Bug bug = new Bug();
        bug.setProgram(programService.findById(programID));

        model.addAttribute("bug", bug);

        logger.debug("Renders request: GET /bugs/add/" + programID);
        return "bugs/new";
    }

    /**
     * Saves new bug.
     * 
     * Creates bugs and redirects to /bugs/{programID} on POST /server/bugs/add
     * request.
     * 
     * On errors let JSF run /views/bugs/new.jsp.
     * 
     * @param bug
     *            bug
     * @param result
     *            validation result
     * @param model
     *            passed model
     * @return facelet name or redirect
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute(value = "bug") Bug bug, BindingResult result,
            Model model) {
        logger.debug("Received request: POST /bugs/add");

        if (result.hasErrors()) {
            model.addAttribute("bug", bug);

            logger.debug("Renders request: POST /bugs/add (validation failed)");
            return "packages/new";
        }

        bugService.persist(bug);

        logger.debug("Redirect to /bugs/" + bug.getProgram().getId() + " (Bug created)");
        return "redirect:" + bug.getProgram().getId();
    }

    /**
     * Renders form to edit existing bug.
     * 
     * Let JSF run /views/bugs/edit.jsp on GET /server/bugs/edit/{id} request.
     * 
     * @param id
     *            bug's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") int id, Model model) {
        logger.debug("Received request: GET /bugs/edit/" + id);

        model.addAttribute("bug", bugService.findById(id));

        logger.debug("Renders request: GET /bugs/edit/" + id);
        return "bugs/edit";
    }

    /**
     * Saves changes.
     * 
     * Updates program and redirects to /bugs/{programID} on POST
     * /server/bugs/edit request.
     * 
     * On errors let JSF run /views/bugs/edit.jsp.
     * 
     * @param bug
     *            bug
     * @param id
     *            bug's id
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("bug") Bug bug, BindingResult result, Model model) {
        logger.debug("Received request: POST /bugs/edit");

        if (result.hasErrors()) {
            model.addAttribute("bug", bug);

            logger.debug("Renders request: POST /bugs/edit (validation failed)");
            return "bugs/edit";
        }

        bugService.merge(bug);

        logger.debug("Redirect to /packages/" + bug.getProgram().getId() + " (Package updated)");
        return "redirect:" + bug.getProgram().getId();
    }

    /**
     * Deletes bug.
     * 
     * Deletes bug and redirects to /bugs/{programID} on GET
     * /server/packages/delete/{id} request.
     * 
     * @param id
     *            bugs's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id, Model model) {
        logger.debug("Received request: GET /bugs/delete/" + id);

        Bug bug = bugService.findById(id);
        int programID = bug.getProgram().getId();
        bugService.remove(bug);

        logger.debug("Redirect to /bugs/" + programID + " (Bug deleted)");
        return "redirect:./../" + programID;
    }

    /**
     * Filters values passed to command object "bug".
     * 
     * @param binder
     *            binder that will bind sent information to command object
     */
    @InitBinder("bug")
    public void configureBindingOfBug(WebDataBinder binder) {
        logger.debug("Securing \"bug\" modelAttribute");
        binder.setAllowedFields("description");
    }
}

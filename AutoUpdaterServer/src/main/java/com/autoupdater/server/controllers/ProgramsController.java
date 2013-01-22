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

import com.autoupdater.server.models.Program;
import com.autoupdater.server.utils.authentication.CurrentUserUtil;

/**
 * Responsible for managing programs.
 */
@Controller
@SessionAttributes({ "program" })
@RequestMapping(value = "/programs")
public final class ProgramsController extends AppController {
    /**
     * Controller's logger.
     */
    private static Logger logger = Logger.getLogger(ProgramsController.class);

    /**
     * Renders programs list.
     * 
     * Let JSF run /views/programs/index.jsp on GET /server/programs request.
     * 
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        logger.debug("Received request: GET /programs");

        model.addAttribute("user", userService.findByUsername(CurrentUserUtil.getUsername()));
        model.addAttribute("programs", programService.findAll());

        logger.debug("Renders requiest: GET /programs");
        return "programs/index";
    }

    /**
     * Renders new program form.
     * 
     * Let JSF run /views/programs/new.jsp on GET /server/programs/add request.
     * 
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createForm(Model model) {
        logger.debug("Received request: GET /programs/add");

        model.addAttribute("program", new Program());

        logger.debug("Renders request: GET /programs/add");
        return "programs/new";
    }

    /**
     * Saves new program.
     * 
     * Updates program and redirects to /programs on POST /server/programs/add
     * request.
     * 
     * On errors let JSF run /views/programs/new.jsp.
     * 
     * @param program
     *            program to be added
     * @param result
     *            validation result
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("program") Program program, BindingResult result,
            Model model) {
        logger.debug("Received request: POST /programs/add");

        if (result.hasErrors()) {
            model.addAttribute("program", program);

            logger.debug("Renders request: POST /programs/add (validation failed)");
            return "programs/new";
        }

        programService.persist(program);

        logger.debug("Redirect to /programs (Program created)");
        return "redirect:";
    }

    /**
     * Renders program edition form.
     * 
     * Let JSF run /views/programs/edit.jsp on GET /server/programs/edit/{id}
     * request.
     * 
     * @param id
     *            program's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") int id, Model model) {
        logger.debug("Received request: GET /programs/edit/" + id);

        model.addAttribute("program", programService.findById(id));

        logger.debug("Renders request: GET /programs/edit/" + id);
        return "programs/edit";
    }

    /**
     * Saves changes.
     * 
     * Updates program and redirects to /programs on POST /server/programs/edit
     * request.
     * 
     * On errors let JSF run /views/programs/edit.jsp.
     * 
     * @param program
     *            program to be changed
     * @param result
     *            validation result
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("program") Program program, BindingResult result,
            Model model) {
        logger.debug("Received request: POST /programs/edit");

        if (result.hasErrors()) {
            model.addAttribute("program", program);

            logger.debug("Renders request: POST /programs/edit (validation failed)");
            return "programs/edit";
        }

        programService.merge(program);

        logger.debug("Redirect to /programs (Program updated)");
        return "redirect:";
    }

    /**
     * Deletes program.
     * 
     * Deletes program and redirects to /programs on GET
     * /server/programs/delete/{id} request.
     * 
     * @param id
     *            program's ID
     * @param model
     *            passed model
     * @return facelet name
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id, Model model) {
        logger.debug("Received request: GET /programs/delete/" + id);

        programService.remove(programService.findById(id));

        logger.debug("Redirect to /programs (Program deleted)");
        return "redirect:./..";
    }

    /**
     * Filters values passed to command object "program".
     * 
     * @param binder
     *            binder that will bind sent information to command object
     */
    @InitBinder("program")
    public void configureBindingOfProgram(WebDataBinder binder) {
        logger.debug("Securing \"program\" modelAttribute");
        binder.setAllowedFields("name");
    }
}

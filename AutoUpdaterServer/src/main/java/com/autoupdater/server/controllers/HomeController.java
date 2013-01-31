package com.autoupdater.server.controllers;

import static org.apache.log4j.Logger.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autoupdater.server.utils.authentication.CurrentUserUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public final class HomeController extends AppController {
    /**
     * Controller's logger.
     */
    private static final Logger logger = getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = GET)
    public String home(Model model) {
        logger.debug("Received request: GET /");

        if (CurrentUserUtil.getUsername() != null) {
            logger.debug("Redirect to: GET /programs");
            return "redirect:/programs";
        }
        logger.debug("Redirect to: GET /sign_in");
        return "redirect:/programs";
    }

}

package com.autoupdater.server.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public final class HomeController extends AppController {
    /**
     * Controller's logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        logger.debug("Received request: GET /");

        model.addAttribute(
                "serverTime",
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale).format(
                        new Date()));

        logger.debug("Renders request: GET /");
        return "home/index";
    }

}

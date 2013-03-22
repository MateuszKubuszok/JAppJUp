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
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
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
     * 
     * @return facelet name
     */
    @RequestMapping(value = "/", method = GET)
    public String home() {
        logger.debug("Received request: GET /");

        if (CurrentUserUtil.getUsername() != null) {
            logger.debug("Redirect to: GET /programs");
            return "redirect:/programs";
        }
        logger.debug("Redirect to: GET /sign_in");
        return "redirect:/programs";
    }

}

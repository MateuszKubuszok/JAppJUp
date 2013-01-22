package com.autoupdater.server.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.autoupdater.server.models.Package;
import com.autoupdater.server.models.Program;
import com.autoupdater.server.models.Update;
import com.autoupdater.server.xml.models.BugsXML;
import com.autoupdater.server.xml.models.ChangelogsXML;
import com.autoupdater.server.xml.models.ProgramsXML;
import com.autoupdater.server.xml.models.UpdatesXML;

/**
 * Responsible for rendering information for client and sending files.
 */
@Controller
@RequestMapping("/api")
public final class FrontEndAPIController extends AppController {
    /**
     * Controller's logger.
     */
    private static Logger logger = Logger.getLogger(FrontEndAPIController.class);

    /**
     * Renders list of packages on server.
     * 
     * Runs on GET /server/api/list_repo request.
     * 
     * @param response
     *            response to be sent
     * @return response's content
     */
    @RequestMapping(value = "/list_repo", method = RequestMethod.GET)
    public @ResponseBody
    ProgramsXML getPackagesListInXML(HttpServletResponse response) {
        logger.debug("Received request: GET /api/list_repo");

        response.setContentType("text/xml");

        logger.debug("Renders request: GET /api/list_repo");
        return new ProgramsXML(programService.findAll());
    }

    /**
     * Renders program's bugs by its ID.
     * 
     * Runs on GET /server/api/list_bugs request.
     * 
     * @param programName
     *            program's ID
     * @param response
     *            response that will be sent
     * @return response's content
     */
    @RequestMapping(value = "/list_bugs/{programName}", method = RequestMethod.GET)
    public @ResponseBody
    BugsXML getBugs(@PathVariable("programName") String programName, HttpServletResponse response) {
        logger.debug("Received request: GET /api/list_bugs/" + programName);

        Program program = programService.findByName(programName);
        if (program == null) {
            logger.debug("Response 404, Program not found for: GET /api/list_bugs/" + programName);
            sendError(response, HttpServletResponse.SC_NOT_FOUND, "Program " + programName
                    + " not found");
            return null;
        }

        response.setContentType("text/xml");

        logger.debug("Renders request: GET /api/list_bugs/" + programName);
        return new BugsXML(program.getBugs());
    }

    /**
     * Renders information about package's updates by its ID.
     * 
     * Runs on GET /server/api/list_updates/{packageID} request.
     * 
     * @param packageID
     *            package's ID
     * @param response
     *            response that will be sent
     * @return response's content
     */
    @RequestMapping(value = "/list_updates/{packageID}", method = RequestMethod.GET)
    public @ResponseBody
    UpdatesXML getUpdateInXML(@PathVariable("packageID") int packageID, HttpServletResponse response) {
        logger.debug("Received request: GET /api/list_updates/" + packageID);

        Package _package = packageService.findById(packageID);
        if (_package == null) {
            logger.debug("Response 404, Package not found for: GET /api/list_updates/" + packageID);
            sendError(response, HttpServletResponse.SC_NOT_FOUND, "Package id=" + packageID
                    + " not found");
            return null;
        }

        response.setContentType("text/xml");

        logger.debug("Renders request: GET /api/list_updates/" + packageID);
        return new UpdatesXML(updateService.findNewestByPackage(_package));
    }

    /**
     * Renders package's changelog by its ID.
     * 
     * Runs on GET /server/api/list_changes/{packageID} request.
     * 
     * @param packageID
     *            package's ID
     * @param response
     *            response that will be sent
     * @return response's content
     */
    @RequestMapping(value = "/list_changes/{packageID}", method = RequestMethod.GET)
    public @ResponseBody
    ChangelogsXML getChangelogs(@PathVariable("packageID") int packageID,
            HttpServletResponse response) {
        logger.debug("Received request: GET /api/list_changes/" + packageID);

        Package _package = packageService.findById(packageID);
        if (_package == null) {
            logger.debug("Response 404, Package not found for: GET /api/list_updates/" + packageID);
            sendError(response, HttpServletResponse.SC_NOT_FOUND, "Package id=" + packageID
                    + " not found");
            return null;
        }

        response.setContentType("text/xml");

        logger.debug("Renders request: GET /api/list_changes/" + packageID);
        return new ChangelogsXML(_package.getUpdates());
    }

    /**
     * Send file to client.
     * 
     * Runs on GET /server/api/download/{updateID} request.
     * 
     * @param updateID
     *            update's ID
     * @param response
     *            response to be sent
     * @return response's content - file
     */
    @RequestMapping(value = "/download/{updateID}", method = RequestMethod.GET)
    public @ResponseBody
    void getFile(@PathVariable int updateID, HttpServletResponse response,
            HttpServletRequest request) {
        try {
            logger.debug("Received request: GET /api/download/" + updateID);

            Update update = updateService.findById(updateID);
            if (update == null) {
                logger.debug("Response 404, Update not found for: GET /api/list_updates/"
                        + updateID);
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Update id=" + updateID
                        + " not found");
                return;
            }

            InputStream is = fileService.loadFile(update.getFileData());

            String range = request.getHeader("Range");
            long skip = 0;
            if (range != null) {
                logger.debug("Values of range header : " + range);
                range = range.substring("bytes=".length());
                skip = Long.parseLong(range);

                is.skip(skip);
            }

            response.setContentType(update.getFileType());
            response.setContentLength((int) (update.getFileSize() - skip));

            logger.debug("Sending file on request: GET /api/download/" + updateID);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (NumberFormatException | IOException e) {
            logger.error("Error sending file updateID=" + updateID + ": " + e);
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Couldn't prepare file to send");
        }
    }
}

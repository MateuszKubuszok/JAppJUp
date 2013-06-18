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

import static java.lang.Long.parseLong;
import static javax.servlet.http.HttpServletResponse.*;
import static org.apache.commons.io.IOUtils.copy;
import static org.apache.log4j.Logger.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.autoupdater.server.commands.RemoteUpdateUploadByFile;
import com.autoupdater.server.models.EUpdateStrategy;
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
    private static final Logger logger = getLogger(FrontEndAPIController.class);

    /**
     * Renders list of packages on server.
     * 
     * Runs on GET /server/api/list_repo request.
     * 
     * @param response
     *            response to be sent
     * @return response's content
     */
    @RequestMapping(value = "/list_repo", method = GET)
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
    @RequestMapping(value = "/list_bugs/{programName}", method = GET)
    public @ResponseBody
    BugsXML getBugs(@PathVariable("programName") String programName, HttpServletResponse response) {
        logger.debug("Received request: GET /api/list_bugs/" + programName);

        Program program = programService.findByName(programName);
        if (program == null) {
            logger.debug("Response 404, Program not found for: GET /api/list_bugs/" + programName);
            sendError(response, SC_NOT_FOUND, "Program " + programName + " not found");
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
    @RequestMapping(value = "/list_updates/{packageID}", method = GET)
    public @ResponseBody
    UpdatesXML getUpdateInXML(@PathVariable("packageID") int packageID, HttpServletResponse response) {
        logger.debug("Received request: GET /api/list_updates/" + packageID);

        Package _package = packageService.findById(packageID);
        if (_package == null) {
            logger.debug("Response 404, Package not found for: GET /api/list_updates/" + packageID);
            sendError(response, SC_NOT_FOUND, "Package id=" + packageID + " not found");
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
    @RequestMapping(value = "/list_changes/{packageID}", method = GET)
    public @ResponseBody
    ChangelogsXML getChangelogs(@PathVariable("packageID") int packageID,
            HttpServletResponse response) {
        logger.debug("Received request: GET /api/list_changes/" + packageID);

        Package _package = packageService.findById(packageID);
        if (_package == null) {
            logger.debug("Response 404, Package not found for: GET /api/list_updates/" + packageID);
            sendError(response, SC_NOT_FOUND, "Package id=" + packageID + " not found");
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
     * @param request
     *            received by servlet
     */
    @SuppressWarnings("resource")
    @RequestMapping(value = "/download/{updateID}", method = GET)
    public @ResponseBody
    void getFile(@PathVariable("updateID") int updateID, HttpServletResponse response,
            HttpServletRequest request) {
        InputStream is = null;
        try {
            logger.debug("Received request: GET /api/download/" + updateID);

            Update update = updateService.findById(updateID);
            if (update == null) {
                logger.debug("Response 404, Update not found for: GET /api/list_updates/"
                        + updateID);
                sendError(response, SC_NOT_FOUND, "Update id=" + updateID + " not found");
                return;
            }

            is = fileService.loadFile(update.getFileData());

            String range = request.getHeader("Range");
            long skip = 0;
            if (range != null) {
                logger.debug("Values of range header : " + range);
                range = range.substring("bytes=".length());
                skip = parseLong(range);

                is.skip(skip);
            }
            response.setContentType(update.getFileType());
            response.setContentLength((int) (update.getFileSize() - skip));

            logger.debug("Sending file on request: GET /api/download/" + updateID);
            copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (NumberFormatException | IOException e) {
            logger.error("Error sending file updateID=" + updateID + ": " + e);
            sendError(response, SC_INTERNAL_SERVER_ERROR, "Couldn't prepare file to send");
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    logger.debug(e);
                }
        }
    }

    /**
     * Generates form for Update update by file.
     * 
     * Runs on GET /server/api/upload_file request.
     * 
     * @param model
     *            model of an upload form
     * @return facelet's name
     */
    @RequestMapping(value = "/upload_file", method = GET)
    public String remoteUploadByFileForm(Model model) {
        logger.debug("Received request: GET /api/upload_file");

        model.addAttribute("remoteUpdateUpload", new RemoteUpdateUploadByFile());
        model.addAttribute("updateTypes", EUpdateStrategy.values());

        logger.debug("Renders request: GET /api/upload_file");
        return "api/remoteUploadByFile";
    }

    /**
     * Uploads update or renders errors.
     * 
     * @param shouldDisplayError
     *            whether error should be returned instead of page with errors
     * @param remoteUpload
     *            Update upload
     * @param result
     *            result of Update validation
     * @param model
     *            model instance
     * @param response
     *            response to send
     * @return facelet name
     */
    @RequestMapping(value = "/upload_file", method = POST)
    public String remoteUploadByFile(
            @RequestParam(value = "displayError", required = false) boolean shouldDisplayError,
            @Valid @ModelAttribute("remoteUpdateUpload") RemoteUpdateUploadByFile remoteUpload,
            BindingResult result, Model model, HttpServletResponse response) {
        logger.debug("Received request: POST /api/upload_file");

        model.addAttribute("remoteUpdateUpload", remoteUpload);

        if (result.hasErrors()) {
            model.addAttribute("updateTypes", EUpdateStrategy.values());

            logger.debug("Renders request: POST /api/upload_file (validation failed)");
            if (shouldDisplayError) {
                handleErrorDuringRemoteUpload(response, result);
                return null;
            }
            return "api/remoteUploadByFile";
        }

        try {
            updateService.persist(remoteUpload.getUpdate());
        } catch (IOException e) {
            model.addAttribute("updateTypes", EUpdateStrategy.values());

            logger.error("Renders request: POST /api/upload_file (file save failed)");
            if (shouldDisplayError) {
                handleErrorDuringRemoteUpload(response, result);
                return null;
            }
            return "api/remoteUploadByFile";
        }

        logger.debug("Renders request: POST /api/upload_file (upload success)");
        return "api/remoteUploadSuccess";
    }

    /**
     * Handles errors that occurred during remote upload.
     * 
     * @param response
     *            response to send
     * @param result
     *            result of validation
     */
    private void handleErrorDuringRemoteUpload(HttpServletResponse response, BindingResult result) {
        int errorCode;
        List<FieldError> errors;

        if (result.hasFieldErrors("username")) {
            errorCode = SC_UNAUTHORIZED;
            errors = result.getFieldErrors("username");
        } else if (result.hasFieldErrors("program")) {
            errorCode = SC_FORBIDDEN;
            errors = result.getFieldErrors("username");
        } else {
            errorCode = SC_BAD_REQUEST;
            errors = result.getFieldErrors();
        }

        StringBuilder builder = new StringBuilder();
        for (FieldError error : errors)
            builder.append(error.getDefaultMessage()).append('\n');
        sendError(response, errorCode, builder.toString());
    }
}

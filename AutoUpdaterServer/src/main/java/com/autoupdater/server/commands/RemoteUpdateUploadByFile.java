package com.autoupdater.server.commands;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.autoupdater.server.constraints.LoggedRemotely;

/**
 * Command objects used for remote update upload by file.
 */
@LoggedRemotely(packageAdmin = true)
public class RemoteUpdateUploadByFile extends RemoteUpdateUpload {
    public CommonsMultipartFile getFile() {
        return update.getFile();
    }

    public void setFile(CommonsMultipartFile file) {
        update.setFile(file);
    }
}

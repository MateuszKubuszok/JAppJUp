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
package com.autoupdater.server.commands;

import javax.validation.Valid;

import com.autoupdater.server.constraints.LoggedRemotely;
import com.autoupdater.server.constraints.ProgramAndPackageExist;
import com.autoupdater.server.models.EUpdateStrategy;
import com.autoupdater.server.models.Update;

/**
 * Command objects used for remote update upload.
 */
@LoggedRemotely(packageAdmin = true)
@ProgramAndPackageExist
public abstract class RemoteUpdateUpload {
    private String username;
    private String password;
    private String program;
    private String thePackage;

    @Valid
    protected final Update update;

    public RemoteUpdateUpload() {
        update = new Update();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getThePackage() {
        return thePackage;
    }

    public void setThePackage(String _package) {
        this.thePackage = _package;
    }

    public String getVersion() {
        return update.getVersion();
    }

    public void setVersion(String version) {
        update.setVersion(version);
    }

    public boolean isDevelopmentVersion() {
        return update.isDevelopmentVersion();
    }

    public void setDevelopmentVersion(boolean developmentVersion) {
        update.setDevelopmentVersion(developmentVersion);
    }

    public String getChangelog() {
        return update.getChangelog();
    }

    public void setChangelog(String changelog) {
        update.setChangelog(changelog);
    }

    public EUpdateStrategy getType() {
        return update.getType();
    }

    public void setType(EUpdateStrategy type) {
        update.setType(type);
    }

    public String getRelativePath() {
        return update.getRelativePath();
    }

    public void setRelativePath(String relativePath) {
        update.setRelativePath(relativePath);
    }

    public String getUpdaterCommand() {
        return update.getUpdaterCommand();
    }

    public void setUpdaterCommand(String updaterCommand) {
        update.setUpdaterCommand(updaterCommand);
    }

    public Update getUpdate() {
        return update;
    }
}

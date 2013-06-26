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
package com.autoupdater.client.models;

import java.io.File;

/**
 * Builder that creates Update instances.
 * 
 * @see com.autoupdater.client.models.Update
 */
public class UpdateBuilder {
    private final Update update;

    private UpdateBuilder() {
        update = new Update();
    }

    /**
     * Creates new UpdateBuilder.
     * 
     * @return UpdateBuilder
     */
    public static UpdateBuilder builder() {
        return new UpdateBuilder();
    }

    public UpdateBuilder copy(Update update) {
        this.update.setChanges(update.getChanges());
        this.update.setCommand(update.getCommand());
        this.update.setDevelopmentVersion(update.isDevelopmentVersion());
        this.update.setFile(update.getFile());
        this.update.setID(update.getID());
        this.update.setOriginalName(update.getOriginalName());
        this.update.setPackage(update.getPackage());
        this.update.setPackageID(update.getPackageID());
        this.update.setPackageName(update.getPackageName());
        this.update.setRelativePath(update.getRelativePath());
        this.update.setStatus(update.getStatus());
        this.update.setStatusMessage(update.getStatusMessage());
        this.update.setUpdateStrategy(update.getUpdateStrategy());
        this.update.setVersionNumber(update.getVersionNumber());
        return this;
    }

    public UpdateBuilder setPackage(Package _package) {
        update.setPackage(_package);
        return this;
    }

    public UpdateBuilder setPackageName(String packageName) {
        update.setPackageName(packageName);
        return this;
    }

    public UpdateBuilder setPackageID(String packageID) {
        update.setPackageID(packageID);
        return this;
    }

    public UpdateBuilder setDevelopmentVersion(String developmentVersion) {
        update.setDevelopmentVersion(developmentVersion);
        return this;
    }

    public UpdateBuilder setDevelopmentVersion(boolean developmentVersion) {
        update.setDevelopmentVersion(developmentVersion);
        return this;
    }

    public UpdateBuilder setID(String id) {
        update.setID(id);
        return this;
    }

    public UpdateBuilder setChanges(String changes) {
        update.setChanges(changes);
        return this;
    }

    public UpdateBuilder setVersionNumber(String versionNumber) {
        update.setVersionNumber(versionNumber);
        return this;
    }

    public UpdateBuilder setVersionNumber(VersionNumber versionNumber) {
        update.setVersionNumber(versionNumber);
        return this;
    }

    public UpdateBuilder setUpdateStrategy(String updateStrategy) {
        update.setUpdateStrategy(updateStrategy);
        return this;
    }

    public UpdateBuilder setUpdateStrategy(EUpdateStrategy updateStrategy) {
        update.setUpdateStrategy(updateStrategy);
        return this;
    }

    public UpdateBuilder setOriginalName(String originalName) {
        update.setOriginalName(originalName);
        return this;
    }

    public UpdateBuilder setRelativePath(String relativePath) {
        update.setRelativePath(relativePath);
        return this;
    }

    public UpdateBuilder setCommand(String command) {
        update.setCommand(command);
        return this;
    }

    public UpdateBuilder setFile(File file) {
        update.setFile(file);
        return this;
    }

    public UpdateBuilder setStatus(String status) {
        update.setStatus(status);
        return this;
    }

    public UpdateBuilder setStatus(EUpdateStatus status) {
        update.setStatus(status);
        return this;
    }

    /**
     * Builds Update.
     * 
     * @return Update
     */
    public Update build() {
        return update;
    }
}

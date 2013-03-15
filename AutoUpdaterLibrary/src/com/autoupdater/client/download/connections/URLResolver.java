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
package com.autoupdater.client.download.connections;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.autoupdater.client.environment.settings.ProgramSettings;

/**
 * Resolves URLs for specified program.
 * 
 * <p>
 * Should be created and used only by PerProgramConnectionFactory.
 * </p>
 */
public class URLResolver {
    private final ProgramSettings programSettings;

    /**
     * Creates URLResolver instance.
     * 
     * @param programSettings
     *            program's settings
     */
    URLResolver(ProgramSettings programSettings) {
        this.programSettings = programSettings;
    }

    /**
     * Resolves URL address for obtaining packages info for specified program.
     * 
     * @return URL address
     */
    public String getPackagesInfoURL() {
        return sanitize(programSettings.getServerAddress() + "/api/list_repo");
    }

    /**
     * Resolves URL address for obtaining update info for specified program.
     * 
     * @param packageID
     *            ID of package for which update info should be obtained
     * @return URL address
     */
    public String getUpdateInfoURL(String packageID) {
        return sanitize(programSettings.getServerAddress() + "/api/list_updates/" + packageID);
    }

    /**
     * Resolves URL address for obtaining changelog for specified package.
     * 
     * @param packageID
     *            ID of package for which changelog should be obtained
     * @return URL address
     */
    public String getChangelogInfoURL(String packageID) {
        return sanitize(programSettings.getServerAddress() + "/api/list_changes/" + packageID);
    }

    /**
     * Resolves URL address for obtaining bugs for specified program.
     * 
     * @param programName
     *            name of package for which changelog should be obtained
     * @return URL address
     */
    public String getBugsInfoURL(String programName) {
        return sanitize(programSettings.getServerAddress() + "/api/list_bugs/" + programName);
    }

    /**
     * Resolves URL address for obtaining update file for specified program.
     * 
     * @param updateID
     *            ID of update for which update file should be obtained
     * @return URL address
     */
    public String getFileURL(String updateID) {
        return sanitize(programSettings.getServerAddress() + "/api/download/" + updateID);
    }

    /**
     * Ensures URL returned by resolver doesn't have disallowed characters.
     * 
     * @param url
     *            URL to sanitize
     * @return sanitized URL
     */
    private String sanitize(String url) {
        try {
            URL sanitizer = new URL(url);
            return new URI(sanitizer.getProtocol(), sanitizer.getAuthority(), sanitizer.getPath(),
                    sanitizer.getQuery(), sanitizer.getRef()).toURL().toString();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("Invalid URL format");
        }
    }
}

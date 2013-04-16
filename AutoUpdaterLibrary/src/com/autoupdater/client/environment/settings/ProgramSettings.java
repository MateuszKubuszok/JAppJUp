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
package com.autoupdater.client.environment.settings;

import static com.autoupdater.client.utils.comparables.Comparables.compare;
import static com.google.common.base.Objects.equal;

import java.util.Comparator;
import java.util.SortedSet;

import com.autoupdater.client.models.IModel;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.utils.comparables.Comparables;

/**
 * Class containing Program Settings. Describes settings concerning one
 * specified Program, e.g. server address, type of installation, executable's
 * name.
 * 
 * <p>
 * In each client instance there should be exactly one ClientSettings instance,
 * obtained through EnvionmentData.
 * </p>
 * 
 * <p>
 * General settings are stored inside ClientSettings class.
 * </p>
 * 
 * @see com.autoupdater.client.environment.EnvironmentData
 * @see com.autoupdater.client.environment.settings.ClientSettings
 */
public class ProgramSettings implements IModel<ProgramSettings> {
    private String programName;
    private String programExecutableName;
    private String pathToProgramDirectory;
    private String pathToProgram;
    private String serverAddress;
    private boolean developmentVersion;

    /**
     * Creates instance of ProgramSettings.
     */
    public ProgramSettings() {
    }

    /**
     * Returns program's name.
     * 
     * <p>
     * Used by client to recognize program on server/repository. Should be
     * exactly the same with program's name obtained through PackagesInfo
     * request.
     * </p>
     * 
     * @return program's name
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Sets program's name.
     * 
     * @param programName
     *            program's name
     */
    public void setProgramName(String programName) {
        this.programName = programName != null ? programName : "";
    }

    /**
     * Returns program's executable name.
     * 
     * <p>
     * Used by client to send TERM/KILL signal. Should be the name allowing to
     * identify process on process list.
     * </p>
     * 
     * @return program's executable name
     */
    public String getProgramExecutableName() {
        return programExecutableName;
    }

    /**
     * Sets program's executable's name.
     * 
     * @param programExecutableName
     *            program's executable's name
     */
    void setProgramExecutableName(String programExecutableName) {
        this.programExecutableName = programExecutableName != null ? programExecutableName : "";
    }

    /**
     * Returns path to program.
     * 
     * <p>
     * Used by client to start program after update.
     * </p>
     * 
     * @return path to program
     */
    public String getPathToProgram() {
        return pathToProgram;
    }

    /**
     * Sets path to program.
     * 
     * @param pathToProgram
     *            path to program
     */
    public void setPathToProgram(String pathToProgram) {
        this.pathToProgram = pathToProgram != null ? pathToProgram : "";
    }

    /**
     * Returns path to program's directory.
     * 
     * <p>
     * All update's installation/extraction paths will be relative to this
     * directory. It should be set bearing that in mind.
     * </p>
     * 
     * @return path to program's directory
     */
    public String getPathToProgramDirectory() {
        return pathToProgramDirectory;
    }

    /**
     * Sets path to program's directory.
     * 
     * @param pathToProgramDirectory
     *            path to program's directory
     */
    public void setPathToProgramDirectory(String pathToProgramDirectory) {
        this.pathToProgramDirectory = pathToProgramDirectory != null ? pathToProgramDirectory : "";
    }

    /**
     * Returns server's address.
     * 
     * <p>
     * All server request will be made relative to this address. It should be
     * set bearing that in mind. It shouldn't end with "/" character.
     * </p>
     * 
     * @return server's address
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * Sets server address - ensures that server address starts with HTTP
     * protocol definition (http:// or https://).
     * 
     * @param address
     *            update server address
     */
    void setServerAddress(String address) {
        serverAddress = address;
        if (serverAddress == null)
            return;

        if (serverAddress.endsWith("/"))
            serverAddress = serverAddress.substring(0, serverAddress.length() - 1);
        if (!serverAddress.startsWith("http://") && !serverAddress.startsWith("https://"))
            serverAddress = "http://" + serverAddress;
    }

    /**
     * Whether or not this program should use development version updates.
     * 
     * @return true if program is in development version, false otherwise
     */
    public boolean isDevelopmentVersion() {
        return developmentVersion;
    }

    public void setDevelopmentVersion(boolean developmentVersion) {
        this.developmentVersion = developmentVersion;
    }

    /**
     * Sets development version using String from configuration file.
     * 
     * <p>
     * Sets version to true if String is equal to "true", false otherwise.
     * </p>
     * 
     * @param developmentVersion
     *            String from configuration file
     */
    void setDevelopmentVersion(String developmentVersion) {
        this.developmentVersion = "true".equalsIgnoreCase(developmentVersion);
    }

    /**
     * Searches set of Programs returning the one, which settings is this
     * instance.
     * 
     * @param programs
     *            set of programs
     * @return program if found, null otherwise
     */
    public Program findProgramForSettings(SortedSet<Program> programs) {
        if (programs != null)
            for (Program program : programs)
                if (settingsBelongsToProgram(program))
                    return program;
        return null;
    }

    /**
     * Whether or not this settings belong to given Program.
     * 
     * @param program
     *            program to check
     * @return true if settings belongs to program, false otherwise
     */
    public boolean settingsBelongsToProgram(Program program) {
        return program != null
                && equal(programName, program.getName())
                && equal(pathToProgramDirectory, program.getPathToProgramDirectory())
                && (equal(serverAddress, program.getServerAddress()) || equal(serverAddress,
                        "http://" + program.getServerAddress()));
    }

    @Override
    public int compareTo(ProgramSettings o) {
        if (o == null)
            return 1;
        else if (o == this)
            return 0;
        else if (compare(programName, o.programName) != 0)
            return compare(programName, o.programName);
        else if (compare(pathToProgramDirectory, o.pathToProgramDirectory) != 0)
            return compare(pathToProgramDirectory, o.pathToProgramDirectory);
        return compare(serverAddress, o.serverAddress);
    }

    @Override
    public Comparator<ProgramSettings> getInstallationsServerPropertiesComparator() {
        return new GeneralComparator();
    }

    @Override
    public Comparator<ProgramSettings> getLocalInstallationsComparator() {
        return new GeneralComparator();
    }

    @Override
    public Comparator<ProgramSettings> getLocal2ServerComparator() {
        return new GeneralComparator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[ProgramSettings]").append('\n');
        builder.append("Name on server:\t\t").append(programName).append('\n');
        builder.append("Installation dir:\t").append(pathToProgramDirectory).append('\n');
        builder.append("Startup command:\t").append(pathToProgram).append('\n');
        builder.append("Name for killer:\t").append(programExecutableName).append('\n');
        builder.append("Server address:\t\t").append(serverAddress).append('\n');
        builder.append("Version type:\t\t").append(developmentVersion ? "development" : "release")
                .append('\n');

        return builder.toString();
    }

    private class GeneralComparator implements Comparator<ProgramSettings> {
        @Override
        public int compare(ProgramSettings o1, ProgramSettings o2) {
            if (o1 == null)
                return (o2 == null ? 0 : -1);
            else if (!equal(o1.programName, o2.programName))
                return Comparables.compare(o1.programName, o2.programName);
            else if (!equal(o1.serverAddress, o2.serverAddress))
                return Comparables.compare(o1.serverAddress, o2.serverAddress);
            return Comparables.compare(o1.pathToProgramDirectory, o2.pathToProgramDirectory);
        }
    }
}

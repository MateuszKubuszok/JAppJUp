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

import static java.util.Arrays.asList;

import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.Paths;
import com.autoupdater.client.Values;

public class MockSettings {
    public static ClientSettings clientSettings() {
        return ClientSettingsBuilder.builder().setClientName(Values.ClientSettings.clientName)
                .setClientExecutableName(Values.ClientSettings.clientExecutableName)
                .setPathToClient(Paths.Library.clientPath)
                .setPathToClientDirectory(Paths.Library.clientDir)
                .setPathToInstaller(Paths.Library.installerPath)
                .setProxyAddress(Values.ClientSettings.proxyAddress)
                .setProxyPort(Values.ClientSettings.proxyPort).build();
    }

    public static ProgramSettings programSettings() {
        return ProgramSettingsBuilder.builder().setProgramName(Values.ProgramSettings.programName)
                .setProgramExecutableName(Values.ProgramSettings.programExecutableName)
                .setPathToProgramDirectory(Paths.Installations.Program.programDir)
                .setPathToProgram(Paths.Installations.Program.programPath)
                .setServerAddress(Values.ProgramSettings.serverAddress)
                .setDevelopmentVersion(Values.ProgramSettings.developmentVersion).build();
    }

    public static ProgramSettings programSettings2() {
        return ProgramSettingsBuilder.builder().setProgramName(Values.ProgramSettings2.programName)
                .setProgramExecutableName(Values.ProgramSettings2.programExecutableName)
                .setPathToProgramDirectory(Paths.Installations.Program2.programDir)
                .setPathToProgram(Paths.Installations.Program2.programPath)
                .setServerAddress(Values.ProgramSettings2.serverAddress)
                .setDevelopmentVersion(Values.ProgramSettings2.developmentVersion).build();
    }

    public static SortedSet<ProgramSettings> programsSettings() {
        return new TreeSet<ProgramSettings>(asList(programSettings(), programSettings2()));
    }
}

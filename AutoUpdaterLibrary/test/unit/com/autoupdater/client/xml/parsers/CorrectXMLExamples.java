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
package com.autoupdater.client.xml.parsers;

import com.autoupdater.client.Paths;
import com.autoupdater.client.Values;

public class CorrectXMLExamples {
    public static final String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    public static final String genericXml = xmlHeader + "<test>test content</test>";
    public static final String clientConfiguration = xmlHeader
            + ("<configuration>"
                    + ("<client name=\""
                            + Values.ClientSettings.clientName
                            + "\" executable=\""
                            + Values.ClientSettings.clientExecutableName
                            + "\">"
                            + ("<locations clientDir=\"" + Paths.Library.clientDir + "\" "
                                    + "console=\"" + Paths.Library.clientPath + "\" "
                                    + "installer=\"" + Paths.Library.installerPath + "\" "
                                    + "uacHandler=\"" + Paths.Library.uacHandlerPath + "\" />")
                            + "<proxy host=\"" + Values.ClientSettings.proxyAddress + "\" port=\""
                            + Values.ClientSettings.proxyPort + "\" />" + "</client>")
                    + ("<programs>"
                            + ("<program name=\"" + Values.ProgramSettings.programName
                                    + "\" executableName=\""
                                    + Values.ProgramSettings.programExecutableName + "\" "
                                    + "programDir=\"" + Paths.Installations.Program.programDir
                                    + "\" " + "console=\""
                                    + Paths.Installations.Program.programPath + "\" "
                                    + "serverURL=\"" + Values.ProgramSettings.serverAddress + "\" "
                                    + "developmentVersion=\""
                                    + String.valueOf(Values.ProgramSettings.developmentVersion) + "\" />")
                            + ("<program name=\"" + Values.ProgramSettings2.programName
                                    + "\" executableName=\""
                                    + Values.ProgramSettings2.programExecutableName + "\" "
                                    + "programDir=\"" + Paths.Installations.Program2.programDir
                                    + "\" " + "console=\""
                                    + Paths.Installations.Program2.programPath + "\" "
                                    + "serverURL=\"" + Values.ProgramSettings2.serverAddress
                                    + "\" " + "developmentVersion=\""
                                    + String.valueOf(Values.ProgramSettings2.developmentVersion) + "\" />") + "</programs>") + "</configuration>");
    public static final String installationData = xmlHeader
            + ("<installed>"
                    + (("<program name=\"" + Values.Program.name + "\" pathToDirectory=\""
                            + Paths.Installations.Program.programDir + "\" serverAddress=\""
                            + Values.Program.serverAddress + "\">")
                            + ("<package name=\"" + Values.Package.name + "\" version=\""
                                    + Values.Package.version + "\" />")
                            + ("<package name=\"" + Values.Package2.name + "\" version=\""
                                    + Values.Package2.version + "\" />") + "</program>")
                    + (("<program name=\"" + Values.Program2.name + "\" pathToDirectory=\""
                            + Paths.Installations.Program2.programDir + "\" serverAddress=\""
                            + Values.Program2.name + "\">")
                            + ("<package name=\"" + Values.Package3.name + "\" version=\""
                                    + Values.Package3.version + "\" />") + "</program>") + "</installed>");
    public static final String packagesInfo = xmlHeader
            + ("<programs>"
                    + ("<program name=\"" + Values.Program.name + "\">"
                            + ("<package name=\"" + Values.Package.name + "\" id=\"1\" />")
                            + ("<package name=\"" + Values.Package2.name + "\" id=\"2\" />") + "</program>")
                    + ("<program name=\"" + Values.Program2.name + "\">"
                            + ("<package name=\"" + Values.Package3.name + "\" id=\"3\" />") + "</program>") + "</programs>");
    public static final String changelogInfo = xmlHeader
            + ("<changelogs>"
                    + ("<changelog version=\"" + Values.Changelog.version + "\">"
                            + Values.Changelog.content + "</changelog>")
                    + ("<changelog version=\"" + Values.Changelog2.version + "\">"
                            + Values.Changelog2.content + "</changelog>") + "</changelogs>");
    public static final String updateInfo = xmlHeader
            + ("<updates>"
                    + ("<update id=\"1\" packageName=\"" + Values.Update.packageName
                            + "\" packageId=\"2\" " + "version=\"" + Values.Update.version
                            + "\" developmentVersion=\"true\" updateType=\"" + Values.Update.type
                            + "\" " + "originalName=\"" + Values.Update.originalName
                            + "\" relativePath=\"" + Values.Update.relativePath + "\" command=\""
                            + Values.Update.updaterCommand + "\">" + Values.Update.changelog + "</update>") + "</updates>");
    public static final String bugsInfo = xmlHeader
            + ("<bugs>" + ("<bug programID=\"1\">" + Values.Bug.description + "</bug>")
                    + ("<bug programID=\"1\">" + Values.Bug2.description + "</bug>") + "</bugs>");
    public static final String fileCache = xmlHeader
            + ("<files>" + ("<file path=\"/file1\" hash=\"1234567890\" />")
                    + ("<file path=\"/file2\" hash=\"0987654321\" />") + "</files>");
}

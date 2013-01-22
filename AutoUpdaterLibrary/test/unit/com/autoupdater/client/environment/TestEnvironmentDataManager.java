package com.autoupdater.client.environment;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.autoupdater.client.AbstractTest;
import com.autoupdater.client.Paths;
import com.autoupdater.client.Values;
import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class TestEnvironmentDataManager extends AbstractTest {
    @Test
    public void testDefaultConstructor() {
        // when
        EnvironmentContext environmentContext = new EnvironmentDataManager().getEnviromentContext();

        // then
        assertThat(environmentContext).as(
                "Contructor should create default environment context properly").isNotNull();
    }

    @Test
    public void testConstructor() {
        // given
        EnvironmentContext originalEnvironmentContext = new EnvironmentContext();

        // when
        EnvironmentContext environmentContext = new EnvironmentDataManager(
                originalEnvironmentContext).getEnviromentContext();

        // then
        assertThat(environmentContext).as("Contructor should set environment context properly")
                .isNotNull().isEqualTo(originalEnvironmentContext);
    }

    @Test
    public void testGetEnvironmentData() throws IOException {
        // given
        File settingsXML = new File(Paths.Setting.settingsXMLPath);
        Files.write(CorrectXMLExamples.clientConfiguration, settingsXML, Charsets.UTF_8);
        settingsXML.deleteOnExit();

        File installationData = new File(Paths.Setting.installationDataXMLPath);
        Files.write(CorrectXMLExamples.installationData, installationData, Charsets.UTF_8);
        installationData.deleteOnExit();

        EnvironmentContext environmentContext = new EnvironmentContext();
        environmentContext.setSettingsXMLPath(Paths.Setting.settingsXMLPath);
        environmentContext.setInstallationDataXMLPath(Paths.Setting.installationDataXMLPath);

        EnvironmentData environmentData = null;
        boolean exceptionThrown = false;

        // when
        try {
            environmentData = new EnvironmentDataManager(environmentContext).getEnvironmentData();
        } catch (ClientEnvironmentException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown)
                .as("getEnvironmentData() should not throw exception while attempting to obtain data from legitimate sources")
                .isFalse();
        assertThat(environmentData).as("getEnvironmentData() should return result properly")
                .isNotNull();
        assertThat(environmentData.getClientSettings()).as(
                "getEnvironmentData() result should contain valid client settings").isNotNull();
        assertThat(environmentData.getProgramsSettings())
                .as("getEnvironmentData() result should contain valid programs' settings")
                .isNotNull().isNotEmpty();
        assertThat(environmentData.getInstallationsData())
                .as("getEnvironmentData() result should contain valid installation data when it's available")
                .isNotNull().isNotEmpty();
    }

    @Test
    public void testSetEnvironmentData() throws ClientEnvironmentException, IOException {
        // given
        File settingsXML = new File(Paths.Setting.settingsXMLPath);
        settingsXML.deleteOnExit();

        File installationData = new File(Paths.Setting.installationDataXMLPath);
        installationData.deleteOnExit();

        EnvironmentContext environmentContext = new EnvironmentContext();
        environmentContext.setSettingsXMLPath(Paths.Setting.settingsXMLPath);
        environmentContext.setInstallationDataXMLPath(Paths.Setting.installationDataXMLPath);

        EnvironmentDataManager environmentDataService = new EnvironmentDataManager(
                environmentContext);

        // when
        environmentDataService.setEnvironmentData(environmentData());
        EnvironmentData savedEnvironmentData = environmentDataService.getEnvironmentData();
        ClientSettings savedClientSettings = savedEnvironmentData.getClientSettings();
        List<ProgramSettings> savedProgramsSettings = new ArrayList<ProgramSettings>(
                savedEnvironmentData.getProgramsSettings());

        // then
        assertThat(savedClientSettings).as(
                "setEnvironmentData(EnvironmentData) should save client's settings").isNotNull();
        assertThat(savedClientSettings.getPathToClientDirectory())
                .as("setEnvironmentData(EnvironmentData) should save client's directory")
                .isNotNull().isEqualTo(Paths.Library.clientDir);
        assertThat(savedClientSettings.getPathToClient())
                .as("setEnvironmentData(EnvironmentData) should save client's executable")
                .isNotNull().isEqualTo(Paths.Library.clientPath);
        assertThat(savedClientSettings.getPathToInstaller())
                .as("setEnvironmentData(EnvironmentData) should save installer's path").isNotNull()
                .isEqualTo(Paths.Library.installerPath);
        ;

        assertThat(savedProgramsSettings)
                .as("setEnvironmentData(EnvironmentData) should save installation data")
                .isNotNull().hasSize(2);
        assertThat(savedProgramsSettings.get(0).getProgramName())
                .as("setEnvironmentData(EnvironmentData) should save program's name").isNotNull()
                .isEqualTo(Values.ProgramSettings.programName);
        assertThat(savedProgramsSettings.get(0).getPathToProgramDirectory())
                .as("setEnvironmentData(EnvironmentData) should save program's directory")
                .isNotNull().isEqualTo(Paths.Installations.Program.programDir);
        assertThat(savedProgramsSettings.get(0).getPathToProgram())
                .as("setEnvironmentData(EnvironmentData) should save path to program").isNotNull()
                .isEqualTo(Paths.Installations.Program.programPath);
        assertThat(savedProgramsSettings.get(0).getServerAddress())
                .as("setEnvironmentData(EnvironmentData) should save server's address").isNotNull()
                .isEqualTo(Values.ProgramSettings.serverAddress);
        assertThat(savedProgramsSettings.get(0).isDevelopmentVersion()).as(
                "setEnvironmentData(EnvironmentData) should save development version").isTrue();
    }

    @Test
    public void testCreateDefaultSettings() {
        // given
        File settingsXML = new File(Paths.Setting.settingsXMLPath);
        settingsXML.deleteOnExit();

        File installationData = new File(Paths.Setting.installationDataXMLPath);
        installationData.deleteOnExit();

        EnvironmentContext environmentContext = new EnvironmentContext();
        environmentContext.setSettingsXMLPath(Paths.Setting.settingsXMLPath);
        environmentContext.setInstallationDataXMLPath(Paths.Setting.installationDataXMLPath);

        // when
        EnvironmentData environmentData = new EnvironmentDataManager(environmentContext)
                .createDefaultSettings();
        ClientSettings clientSettings = environmentData.getClientSettings();
        List<ProgramSettings> programsSettings = new ArrayList<ProgramSettings>(
                environmentData.getProgramsSettings());

        // then
        assertThat(clientSettings).as("createEnvironmentData() should create client's settings")
                .isNotNull();
        assertThat(clientSettings.getPathToClientDirectory())
                .as("createEnvironmentData() should create client's directory").isNotNull()
                .isEqualTo(environmentContext.getDefaultPathToClientDirectory());
        assertThat(clientSettings.getPathToClient())
                .as("createEnvironmentData() should create client's executable").isNotNull()
                .isEqualTo(environmentContext.getDefaultPathToClient());
        assertThat(clientSettings.getPathToInstaller())
                .as("createEnvironmentData() should create installer's path").isNotNull()
                .isEqualTo(environmentContext.getDefaultPathToInstaller());

        assertThat(programsSettings)
                .as("createEnvironmentData() should not create any program's settings").isNotNull()
                .isEmpty();
    }

    @Test
    public void testCreateDefaultSettingsWithProxy() {
        // given
        File settingsXML = new File(Paths.Setting.settingsXMLPath);
        settingsXML.deleteOnExit();

        File installationData = new File(Paths.Setting.installationDataXMLPath);
        installationData.deleteOnExit();

        EnvironmentContext environmentContext = new EnvironmentContext();
        environmentContext.setSettingsXMLPath(Paths.Setting.settingsXMLPath);
        environmentContext.setInstallationDataXMLPath(Paths.Setting.installationDataXMLPath);

        // when
        EnvironmentData environmentData = new EnvironmentDataManager(environmentContext)
                .createDefaultSettingsWithProxy();
        ClientSettings clientSettings = environmentData.getClientSettings();
        List<ProgramSettings> programsSettings = new ArrayList<ProgramSettings>(
                environmentData.getProgramsSettings());

        // then
        assertThat(clientSettings).as(
                "createEnvironmentDataWithProxy() should create client's settings").isNotNull();
        assertThat(clientSettings.getPathToClientDirectory())
                .as("createEnvironmentDataWithProxy() should create client's directory")
                .isNotNull().isEqualTo(environmentContext.getDefaultPathToClientDirectory());
        assertThat(clientSettings.getPathToClient())
                .as("createEnvironmentDataWithProxy() should create client's executable")
                .isNotNull().isEqualTo(environmentContext.getDefaultPathToClient());
        assertThat(clientSettings.getPathToInstaller())
                .as("createEnvironmentDataWithProxy() should create installer's path").isNotNull()
                .isEqualTo(environmentContext.getDefaultPathToInstaller());
        assertThat(clientSettings.getProxyAddress())
                .as("createEnvironmentDataWithProxy() should create proxy's address").isNotNull()
                .isEqualTo(environmentContext.getDefaultProxyAddress());
        assertThat(clientSettings.getProxyPort())
                .as("createEnvironmentDataWithProxy() should create proxy's port").isNotNull()
                .isEqualTo(environmentContext.getDefaultProxyPort());

        assertThat(programsSettings)
                .as("createEnvironmentDataWithProxy() should not create any program's settings")
                .isNotNull().isEmpty();
    }
}

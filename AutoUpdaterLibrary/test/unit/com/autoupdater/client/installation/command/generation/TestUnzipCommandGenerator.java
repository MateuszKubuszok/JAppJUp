package com.autoupdater.client.installation.command.generation;

import static com.autoupdater.client.models.EUpdateStrategy.UNZIP;
import static net.jsdpu.JavaSystemUtils.getJavaExecutablePath;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;

import net.jsdpu.process.executors.InvalidCommandException;

import org.junit.Test;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.models.UpdateBuilder;

public class TestUnzipCommandGenerator {
    @Test
    public void testGenerateCommand() throws InvalidCommandException {
        // given
        File file = new File("mock file");
        Update update = UpdateBuilder.builder().copy(com.autoupdater.client.models.Mocks.update())
                .setFile(file).setUpdateStrategy(UNZIP).setCommand("{F}").build();
        ProgramSettings programSettings = com.autoupdater.client.environment.settings.Mocks
                .programSettings();
        String pathToInstaller = "./Installer.jar";

        // when
        String[] command = new UnzipCommandGenerator().generateCommand(update, pathToInstaller,
                programSettings);

        // then
        assertThat(command).as("Should generate correct command").isEqualTo(
                new String[] { getJavaExecutablePath(), "-jar", "./Installer.jar",
                        update.getUniqueIdentifer(), UNZIP.toString(), file.getAbsolutePath(),
                        programSettings.getPathToProgramDirectory() });
    }
}

package com.autoupdater.client.installation.runnable;

import static com.autoupdater.client.installation.EInstallationStatus.*;
import static com.autoupdater.client.installation.EInstallationStatus.INSTALLING;
import static com.autoupdater.client.models.EUpdateStatus.*;
import static com.autoupdater.client.models.EUpdateStatus.INSTALLED;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

import net.jsdpu.process.executors.ExecutionQueueReader;
import net.jsdpu.process.executors.InvalidCommandException;
import net.jsdpu.process.killers.ProcessKillerException;

import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.installation.EInstallationStatus;
import com.autoupdater.client.installation.InstallationServiceMessage;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.executions.RunnableWithErrors;
import com.autoupdater.client.utils.services.ObservableService;

/**
 * Runnable responsible for calling installer and obtaining results of its
 * execution.
 * 
 * <p>
 * Used by InstallationAggregatedService.
 * </p>
 * 
 * @see com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService
 */
public class InstallationRunnable extends ObservableService<InstallationServiceMessage> implements
        RunnableWithErrors {
    private final EnvironmentData environmentData;
    private final CommandGenerationHelper commandGenerationHelper;
    private final ProcessShutdownHelper processHelper;
    private final SortedSet<Update> updates;
    private EInstallationStatus state = EInstallationStatus.PREPARING_INSTALLATION;

    private List<String[]> updateExecutionCommands;
    private ExecutionQueueReader reader;

    private Throwable thrownException = null;

    /**
     * Creates runnable that will attempt to install required Updates.
     * 
     * @param environmentData
     *            environmentData instance
     * @param updates
     *            Updates to install
     */
    public InstallationRunnable(EnvironmentData environmentData, SortedSet<Update> updates) {
        this.environmentData = environmentData;
        this.commandGenerationHelper = new CommandGenerationHelper(environmentData);
        this.processHelper = new ProcessShutdownHelper(environmentData);
        this.updates = updates;
    }

    /**
     * Starts installation process.
     */
    @Override
    public void run() {
        state = EInstallationStatus.PREPARING_INSTALLATION;
        try {
            killOpenedUpdatedPrograms();
            prepareUpdateCommands();
            installUpdates();
        } catch (ProgramSettingsNotFoundException | IOException | InterruptedException
                | ProcessKillerException | InvalidCommandException e) {
            setThrownException(e);
            reportError(e.getMessage());
        } finally {
            updateEnvironmentDataAndPackagesIfNeccessary();
        }
    }

    /**
     * Returns current state of installation.
     * 
     * @return current state of installation.
     */
    public EInstallationStatus getState() {
        return state;
    }

    @Override
    public Throwable getThrownException() {
        return thrownException;
    }

    @Override
    public void setThrownException(Throwable throwable) {
        thrownException = throwable;
    }

    @Override
    public void throwExceptionIfErrorOccured() throws Throwable {
        if (thrownException != null)
            throw thrownException;
    }

    /**
     * Kills open updated programs.
     * 
     * @throws ProgramSettingsNotFoundException
     *             thrown if ProgramSettings for some Update isn't found
     * @throws ProcessKillerException
     *             thrown if ProcessKiller cannot kill some program
     * @throws IOException
     *             thrown when error occurs in system dependent process
     * @throws InterruptedException
     *             thrown when thread is interrupted during waiting for system
     *             dependent process to finish
     */
    private void killOpenedUpdatedPrograms() throws ProgramSettingsNotFoundException,
            ProcessKillerException, IOException, InterruptedException {
        reportChange("Shutting down applications before update...",
                EInstallationStatus.KILLING_UPDATED_APPLICATIONS);
        processHelper.killProcesses(updates);
        reportChange("Closed all applications", EInstallationStatus.KILLED_UPDATED_APPLICATIONS);
    }

    /**
     * Prepares commands used during update.
     * 
     * @throws ProgramSettingsNotFoundException
     *             thrown if ProgramSettings for some Update isn't found
     * @throws InvalidCommandException
     *             thrown if command passed into ProcessExecutor is invalid
     * @throws IOException
     *             thrown when error occurs in system dependent process
     */
    private void prepareUpdateCommands() throws ProgramSettingsNotFoundException,
            InvalidCommandException, IOException {
        reportChange("Preparing installation commands...", INSTALLING);
        updateExecutionCommands = commandGenerationHelper.getUpdateExecutionCommands(updates);
        reader = environmentData.getSystem().getProcessExecutor()
                .executeRoot(updateExecutionCommands);
    }

    /**
     * Installs updates.
     */
    private void installUpdates() {
        reportChange("Installing updates...", INSTALLING);
        new InstallersOutputParser(environmentData).parseInstallersOutput(updates, reader);
    }

    /**
     * Updates information inside Packages (changes version number if
     * installation finished successfully) and saves EnvironmentData if any of
     * version numbers changed.
     */
    private void updateEnvironmentDataAndPackagesIfNeccessary() {
        boolean errorOccured = false;
        boolean someUpdateInstalled = false;
        for (Update update : updates)
            if (update != null) {
                if (update.getStatus() == INSTALLED)
                    someUpdateInstalled = true;
                else if (update.getStatus().isInstallationFailed()) {
                    update.setStatusMessage("Uknown Installer failure");
                    update.setStatus(INSTALLATION_FAILED);
                    errorOccured = true;
                }
            }

        if (errorOccured)
            reportError("Not all updates were successfully installed");
        else
            reportChange("Updates installed successfully", SUCCEEDED);

        if (someUpdateInstalled)
            try {
                environmentData.save();
            } catch (ClientEnvironmentException | IOException e) {
                reportError("Couldn't save information about newly installed updates");
            }
    }

    /**
     * Reports change together with new installation status.
     * 
     * @param message
     *            message to pass
     * @param state
     *            new installation status
     */
    private void reportChange(String message, EInstallationStatus state) {
        this.state = state;
        hasChanged();
        notifyObservers(new InstallationServiceMessage(message));
    }

    /**
     * Reports error in installation process.
     * 
     * @param message
     *            message to pass
     */
    private void reportError(String message) {
        state = FAILED;
        hasChanged();
        notifyObservers(new InstallationServiceMessage(message, true));
    }
}

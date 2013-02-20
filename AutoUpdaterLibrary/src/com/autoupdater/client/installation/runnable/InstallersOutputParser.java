package com.autoupdater.client.installation.runnable;

import static com.google.common.base.Throwables.propagate;
import static java.util.regex.Pattern.compile;

import java.io.IOException;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.jsdpu.process.executors.ExecutionQueueReader;
import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.enums.Enums;
import com.autoupdater.commons.error.codes.EErrorCode;
import com.autoupdater.commons.messages.EInstallerMessage;
import com.google.common.base.Objects;

/**
 * Helper responsible for reading data from installer's execution log, and
 * obtaining information about e.g. success, failure, backup completion.
 * 
 * <p>
 * Used by InstallationRunnable.
 * </p>
 * 
 * @see com.autoupdater.client.installation.runnable.InstallationRunnable
 */
class InstallersOutputParser {
    private static Pattern INFO_PATTERN = compile("^\\[info\\] ([^:]+): (.+)$");
    private static Pattern ERROR_PATTERN = compile("^\\[error\\] ([^:]+): (.+)$");

    private final EnvironmentData environmentData;

    InstallersOutputParser() {
        environmentData = null;
    }

    InstallersOutputParser(EnvironmentData environmentData) {
        this.environmentData = environmentData;
    }

    /**
     * Parses information from installer's log into information about current
     * state of installation.
     * 
     * @param updates
     *            set of updates
     * @param reader
     *            source of installer's log output
     */
    public void parseInstallersOutput(SortedSet<Update> updates, ExecutionQueueReader reader) {
        String result;

        while (true) {
            try {
                if ((result = reader.getNextOutput()) == null)
                    return;

                Matcher infoMatcher = INFO_PATTERN.matcher(result);
                if (infoMatcher.find()) {
                    parseInfoResult(updates, infoMatcher);
                    continue;
                }

                Matcher errorMatcher = ERROR_PATTERN.matcher(result);
                if (errorMatcher.find()) {
                    parseErrorResult(updates, errorMatcher);
                    continue;
                }
            } catch (InvalidCommandException e) {
                continue;
            }
        }
    }

    /**
     * Parses log's entry containing INFO message.
     * 
     * @param updates
     *            set of updates
     * @param infoMatcher
     *            matcher that found INFO
     */
    private void parseInfoResult(SortedSet<Update> updates, Matcher infoMatcher) {
        EInstallerMessage installerMessage;

        String updateID = infoMatcher.group(1);
        String message = infoMatcher.group(2);

        Update update = findUpdateByID(updates, updateID);
        EUpdateStatus updateStatus;
        if (update != null)
            try {
                if ((installerMessage = Enums.parseMessage(EInstallerMessage.class, message)) != null
                        && (updateStatus = Enums.parseField(EUpdateStatus.class,
                                "installerMessage", installerMessage)) != null)
                    update.setStatus(updateStatus);
                else if (Enums.parseField(EErrorCode.class, "description", message) == EErrorCode.SUCCESS)
                    update.setStatus(EUpdateStatus.INSTALLED);
            } catch (NoSuchFieldException e) {
                propagate(e);
            }
    }

    /**
     * Parses log's entry containing ERROR message.
     * 
     * @param updates
     *            set of updates
     * @param errorMatcher
     *            matcher that found ERROR
     */
    private void parseErrorResult(SortedSet<Update> updates, Matcher errorMatcher) {
        EInstallerMessage installerMessage;

        String updateID = errorMatcher.group(1);
        String message = errorMatcher.group(2);

        Update update = findUpdateByID(updates, updateID);
        EUpdateStatus updateStatus;
        if (update != null) {
            try {
                if ((installerMessage = Enums.parseMessage(EInstallerMessage.class, message)) != null
                        && (updateStatus = Enums.parseField(EUpdateStatus.class,
                                "installerMessage", installerMessage)) != null) {
                    update.setStatus(updateStatus);
                    if (updateStatus == EUpdateStatus.INSTALLED && environmentData != null)
                        try {
                            environmentData.save();
                        } catch (ClientEnvironmentException | IOException e) {
                        }
                } else if (update.getStatus().isIntendedToBeChanged()) {
                    update.setStatusMessage(message);
                    update.setStatus(EUpdateStatus.INSTALLATION_FAILED);
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Wrong installer message field name");
            }
        }
    }

    /**
     * Finds Update in set by its ID.
     * 
     * @param updates
     *            set of updates
     * @param updateID
     *            searched Update's ID
     * @return found update, null otherwise
     */
    private static Update findUpdateByID(SortedSet<Update> updates, String updateID) {
        for (Update update : updates)
            if (Objects.equal(update.getUniqueIdentifer(), updateID))
                return update;
        return null;
    }
}

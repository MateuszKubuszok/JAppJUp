package com.autoupdater.client.models;

import static com.autoupdater.client.models.EUpdateStatus.NOT_SELECTED;
import static com.autoupdater.client.models.Models.equal;
import static com.autoupdater.client.utils.comparables.Comparables.compare;
import static com.google.common.base.Objects.equal;
import static java.lang.Math.pow;
import static java.lang.String.valueOf;

import java.io.File;
import java.util.Comparator;
import java.util.Random;

import com.autoupdater.client.utils.comparables.Comparables;
import com.autoupdater.client.utils.enums.Enums;
import com.autoupdater.client.utils.services.ObservableService;

/**
 * Class representing Update available for installation.
 */
public class Update extends ObservableService<EUpdateStatus> implements IModel<Update> {
    private Package _package;
    private String packageName;
    private String packageID;
    private boolean developmentVersion;
    private String id;
    private String changes;
    private VersionNumber versionNumber;
    private EUpdateStrategy updateStrategy;
    private String originalName;
    private String relativePath;
    private String command;

    private File file;

    private EUpdateStatus status;
    private String statusMessage;

    private String uniqueIdentifier;

    Update() {
        status = NOT_SELECTED;
    }

    /**
     * Returns Program's Package.
     * 
     * @return Program's Package
     */
    public Package getPackage() {
        return _package;
    }

    /**
     * Sets Update's Package.
     * 
     * @param _package
     *            Program's Package
     */
    public void setPackage(Package _package) {
        this._package = _package;
    }

    /**
     * Returns Update's Package's name.
     * 
     * @return Update's Package's name
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Sets Update's Package's name.
     * 
     * @param packageName
     *            Update's Package's name
     */
    void setPackageName(String packageName) {
        this.packageName = packageName != null ? packageName : "";
    }

    /**
     * Returns Update's Package's ID.
     * 
     * @return Program's Package's ID
     */
    public String getPackageID() {
        return packageID;
    }

    /**
     * Sets Update's Package's ID.
     * 
     * @param packageID
     *            Program's Package's ID
     */
    void setPackageID(String packageID) {
        this.packageID = packageID != null ? packageID : "";
    }

    /**
     * Whether Update is for development version.
     * 
     * @return whether Update is for development version
     */
    public boolean isDevelopmentVersion() {
        return developmentVersion;
    }

    /**
     * Sets whether Update is for development version
     * 
     * @param developmentVersion
     *            whether Update is for development version
     */
    void setDevelopmentVersion(boolean developmentVersion) {
        this.developmentVersion = developmentVersion;
    }

    /**
     * Sets whether Update is for development version
     * 
     * @param developmentVersion
     *            whether Update is for development version
     */
    void setDevelopmentVersion(String developmentVersion) {
        this.developmentVersion = "true".equalsIgnoreCase(developmentVersion);
    }

    /**
     * Returns Update's ID.
     * 
     * @return Update's ID
     */
    public String getID() {
        return id;
    }

    /**
     * Sets Update's ID.
     * 
     * @param id
     *            Update's ID
     */
    void setID(String id) {
        this.id = id != null ? id : "";
    }

    /**
     * Returns changes done in this Update.
     * 
     * @return description
     */
    public String getChanges() {
        return changes;
    }

    /**
     * Sets changes done in this Update
     * 
     * @param changes
     *            description
     */
    void setChanges(String changes) {
        this.changes = changes != null ? changes : "";
    }

    /**
     * Returns Update's version number.
     * 
     * @return Update's version number
     */
    public VersionNumber getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets Update's version number.
     * 
     * @param versionNumber
     *            Update's version number
     */
    void setVersionNumber(String versionNumber) {
        this.versionNumber = new VersionNumber(versionNumber);
    }

    /**
     * Sets Update's version number.
     * 
     * @param versionNumber
     *            Update's version number
     */
    void setVersionNumber(VersionNumber versionNumber) {
        this.versionNumber = versionNumber != null ? versionNumber : VersionNumber.UNVERSIONED;
    }

    /**
     * Returns Update strategy.
     * 
     * @return Update strategy
     */
    public EUpdateStrategy getUpdateStrategy() {
        return updateStrategy;
    }

    /**
     * Sets Update strategy.
     * 
     * @param updateStrategy
     *            Update strategy
     */
    void setUpdateStrategy(String updateStrategy) {
        setUpdateStrategy(Enums.parseMessage(EUpdateStrategy.class,
                updateStrategy != null ? updateStrategy.toLowerCase() : ""));
    }

    /**
     * Sets Update strategy.
     * 
     * @param updateStrategy
     *            Update strategy
     */
    void setUpdateStrategy(EUpdateStrategy updateStrategy) {
        this.updateStrategy = updateStrategy != null ? updateStrategy : EUpdateStrategy.COPY;
    }

    /**
     * Returns original Update filename.
     * 
     * @return original Update filename
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * Sets original Update filename.
     * 
     * @param originalName
     *            original Update filename
     */
    void setOriginalName(String originalName) {
        this.originalName = originalName != null ? originalName : "";
    }

    /**
     * Returns relative path for strategy.
     * 
     * @return relative path for strategy
     */
    public String getRelativePath() {
        return relativePath;
    }

    /**
     * Sets relative path for strategy.
     * 
     * @param relativePath
     *            relative path for strategy
     */
    void setRelativePath(String relativePath) {
        relativePath = relativePath != null ? relativePath : "";
        if (relativePath.startsWith("/"))
            relativePath = relativePath.substring(1);
        else if (relativePath.startsWith("\\"))
            relativePath = relativePath.substring(2);
        if (relativePath.endsWith("/"))
            relativePath = relativePath.substring(0, relativePath.length() - 1);
        else if (relativePath.endsWith("\\"))
            relativePath = relativePath.substring(0, relativePath.length() - 2);
        this.relativePath = relativePath.replace(File.separator, File.separator.equals("/") ? "\\"
                : "/");
    }

    /**
     * Returns command to execute after/as update (depending on strategy).
     * 
     * @return command to execute after/as update
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets command to execute after/as update.
     * 
     * @param command
     *            command to execute after/as update
     */
    void setCommand(String command) {
        this.command = command != null ? command : "";
    }

    /**
     * Returns Update's File.
     * 
     * @return Update's File
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets Update's File.
     * 
     * @param file
     *            Update's File
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Returns Update's status.
     * 
     * @return Update's status
     */
    public EUpdateStatus getStatus() {
        return status;
    }

    /**
     * Sets Update's status.
     * 
     * <p>
     * Notifies Update's observers about change.
     * </p>
     * 
     * @param status
     *            Update's status
     */
    public void setStatus(String status) {
        setStatus(Enums.parseMessage(EUpdateStatus.class, status));
    }

    /**
     * Sets Update's status.
     * 
     * <p>
     * Notifies Update's observers about change.
     * </p>
     * 
     * @param status
     *            Update's status
     */
    public void setStatus(EUpdateStatus status) {
        this.status = status;
        if (status == EUpdateStatus.INSTALLED && _package != null)
            _package.setVersionNumber(versionNumber);
        hasChanged();
        notifyObservers(status);
    }

    /**
     * Returns last message related to status change.
     * 
     * @return last message related to status change
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Sets last message related to status change.
     * 
     * @param statusMessage
     *            last message related to status change
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Update))
            return false;
        else if (obj == this)
            return true;

        Update update = (Update) obj;
        return equal(packageName, update.packageName) && equal(versionNumber, update.versionNumber);
    }

    @Override
    public int hashCode() {
        return (int) pow(packageName.hashCode(), 10) + versionNumber.hashCode();
    }

    @Override
    public int compareTo(Update o) {
        if (o == null)
            return 1;
        else if (o == this)
            return 0;
        else if (!equal(packageName, o.packageName))
            return compare(packageName, o.packageName);
        return compare(versionNumber, o.versionNumber);
    }

    @Override
    public String toString() {
        return packageName + " " + versionNumber + ", "
                + (developmentVersion ? "development" : "release");
    }

    public String getUniqueIdentifer() {
        return uniqueIdentifier != null ? uniqueIdentifier
                : (uniqueIdentifier = valueOf(new Random().nextLong()));
    }

    @Override
    public Comparator<Update> getInstallationsServerPropertiesComparator() {
        throw new UnsupportedOperationException("Not implemented - not need to use this!");
    }

    @Override
    public Comparator<Update> getLocalInstallationsComparator() {
        return new LocalInstallationsComparator();
    }

    @Override
    public Comparator<Update> getLocal2ServerComparator() {
        return new Local2ServerComparator();
    }

    private class LocalInstallationsComparator implements Comparator<Update> {
        @Override
        public int compare(Update o1, Update o2) {
            if (o1 == null)
                return o2 == null ? 0 : -1;
            if (!equal(o1.packageName, o2.packageName))
                Comparables.compare(o1.packageName, o2.packageName);
            return Comparables.compare(o1.versionNumber, o2.versionNumber);
        }
    }

    private class Local2ServerComparator implements Comparator<Update> {
        @Override
        public int compare(Update o1, Update o2) {
            if (o1 == null)
                return o2 == null ? 0 : -1;
            if (!equal(o1.packageName, o2.packageName))
                return Comparables.compare(o1.packageName, o2.packageName);
            if (!equal(o1.developmentVersion, o2.developmentVersion))
                return o1.developmentVersion ? 1 : -1;
            if (!equal(o1._package, o2._package, Models.EComparisionType.LOCAL_TO_SERVER))
                return Models.compare(o1._package, o2._package,
                        Models.EComparisionType.LOCAL_TO_SERVER);
            return Models.compare(o1._package.getProgram(), o2._package.getProgram(),
                    Models.EComparisionType.LOCAL_TO_SERVER);
        }
    }
}

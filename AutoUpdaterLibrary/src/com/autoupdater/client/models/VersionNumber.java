package com.autoupdater.client.models;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing version number of Package or Update.
 * 
 * @see com.autoupdater.client.models.Package
 * @see com.autoupdater.client.models.Update
 */
public class VersionNumber implements IModel<VersionNumber> {
    public static final VersionNumber UNVERSIONED = new VersionNumber(0, 0, 0, 0);

    private int major;
    private int minor;
    private int release;
    private int nightly;

    /**
     * Creates new VersionNumber using String as reference.
     * 
     * @param versionNumber
     *            String in format [major].[minor].[release].[nightly]
     */
    public VersionNumber(String versionNumber) {
        parseVersionNumber(versionNumber);
    }

    /**
     * Creates new VersionNumber using respective numbers.
     * 
     * @param major
     *            major version number
     * @param minor
     *            minor version number
     * @param release
     *            release version number
     * @param nightly
     *            nightly version number
     */
    public VersionNumber(int major, int minor, int release, int nightly) {
        this.major = major;
        this.minor = minor;
        this.release = release;
        this.nightly = nightly;
    }

    /**
     * Gets major version number.
     * 
     * @return major version number
     */
    public int getMajor() {
        return major;
    }

    /**
     * Gets minor version number.
     * 
     * @return minor version number
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Gets release version number.
     * 
     * @return release version number
     */
    public int getRelease() {
        return release;
    }

    /**
     * Gets nightly version number.
     * 
     * @return nightly version number
     */
    public int getNightly() {
        return nightly;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof VersionNumber))
            return false;
        else if (obj == this)
            return true;

        VersionNumber versionNumber = (VersionNumber) obj;
        return this.major == versionNumber.major && this.minor == versionNumber.minor
                && this.release == versionNumber.release && this.nightly == versionNumber.nightly;
    }

    @Override
    public int hashCode() {
        return (int) (nightly + Math.pow(release, 2) + Math.pow(minor, 4) + Math.pow(major, 8));
    }

    /**
     * Parses String into respective values.
     * 
     * @param versionNumber
     *            String in format [major].[minor].[release].[nightly]
     */
    private void parseVersionNumber(String versionNumber) {
        major = 0;
        minor = 0;
        release = 0;
        nightly = 0;

        if (versionNumber == null)
            return;

        Pattern pattern = Pattern.compile("^([0-9]+)(\\.([0-9]+)(\\.([0-9]+)(\\.([0-9]+))?)?)?$");
        Matcher matcher = pattern.matcher(versionNumber);

        if (!matcher.find())
            return;

        String checkedMajor = matcher.group(1);
        if (checkedMajor == null || checkedMajor.isEmpty())
            return;
        major = Integer.parseInt(checkedMajor);

        String checkedMinor = matcher.group(3);
        if (checkedMinor == null || checkedMinor.isEmpty())
            return;
        minor = Integer.parseInt(checkedMinor);

        String checkedRelease = matcher.group(5);
        if (checkedRelease == null || checkedRelease.isEmpty())
            return;
        release = Integer.parseInt(checkedRelease);

        String checkedNightly = matcher.group(7);
        if (checkedNightly == null || checkedNightly.isEmpty())
            return;
        nightly = Integer.parseInt(checkedNightly);
    }

    @Override
    public String toString() {
        return String.valueOf(major) + "." + String.valueOf(minor) + "." + String.valueOf(release)
                + "." + String.valueOf(nightly);
    }

    @Override
    public int compareTo(VersionNumber o) {
        if (o == null)
            return 1;
        else if (o == this)
            return 0;
        else if (major != o.major)
            return major - o.major;
        else if (minor != o.minor)
            return minor - o.minor;
        else if (release != o.release)
            return release - o.release;
        return nightly - o.nightly;
    }

    @Override
    public Comparator<VersionNumber> getInstallationsServerPropertiesComparator() {
        return new GeneralComparator();
    }

    @Override
    public Comparator<VersionNumber> getLocalInstallationsComparator() {
        return new GeneralComparator();
    }

    @Override
    public Comparator<VersionNumber> getLocal2ServerComparator() {
        return new GeneralComparator();
    }

    private class GeneralComparator implements Comparator<VersionNumber> {
        @Override
        public int compare(VersionNumber o1, VersionNumber o2) {
            if (o1 == null)
                return (o2 == null ? 0 : -1);
            else if (o1.major != o2.major)
                return o1.major - o2.major;
            else if (o1.minor != o2.minor)
                return o1.minor - o2.minor;
            else if (o1.release != o2.release)
                return o1.release - o2.release;
            return o1.nightly - o2.nightly;
        }
    }

    /**
     * Creates new VersionNumber using String as reference.
     * 
     * @param version
     *            String in format [major].[minor].[release].[nightly]
     * @return VersionNumber instance
     */
    public static VersionNumber version(String version) {
        return new VersionNumber(version);
    }

    /**
     * Creates new VersionNumber using respective numbers.
     * 
     * @param major
     *            major version number
     * @param minor
     *            minor version number
     * @param release
     *            release version number
     * @param nightly
     *            nightly version number
     * @return VersionNumber instance
     */
    public static VersionNumber version(int major, int minor, int release, int nightly) {
        return new VersionNumber(major, minor, release, nightly);
    }
}

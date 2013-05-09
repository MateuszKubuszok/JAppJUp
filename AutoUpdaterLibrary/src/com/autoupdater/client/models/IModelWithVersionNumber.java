package com.autoupdater.client.models;

/**
 * Indicates that Model has VersionNumber that can be for compared to another
 * Model's VersionNumber.
 */
public interface IModelWithVersionNumber {
    /**
     * Returns Model's version number.
     * 
     * @return Model's version number
     */
    public VersionNumber getVersionNumber();

    /**
     * Returns result of comparison with another Model's Version Number.
     * 
     * @param model
     *            Model with Version Number
     * @return result of comparison
     */
    public int compareVersions(IModelWithVersionNumber model);

    /**
     * Returns whether both models have equal version numbers.
     * 
     * @param model
     *            Model with Version Number
     * @return true if both model have the same Version Number
     */
    public boolean equalVersions(IModelWithVersionNumber model);
}

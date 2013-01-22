package com.autoupdater.server.services;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.autoupdater.server.models.Package;

/**
 * Service responsible for managing Packages.
 * 
 * @see com.autoupdater.server.models.Package
 */
@Repository
public interface PackageService {
    /**
     * Persists Package.
     * 
     * @param _package
     *            Package to persist
     */
    public void persist(Package _package);

    /**
     * Merges changes into existing Package.
     * 
     * @param _package
     *            package to merge
     * @return merged package
     */
    public Package merge(Package _package);

    /**
     * Removes Package.
     * 
     * @param _package
     *            Package to remove
     */
    public void remove(Package _package);

    /**
     * Updates detached Package.
     * 
     * @param _package
     *            Package to refresh
     */
    public void refresh(Package _package);

    /**
     * Finds Package by its ID.
     * 
     * @param id
     *            Package's ID
     * @return Package if found, null otherwise
     */
    public Package findById(int id);

    /**
     * Returns all Packages.
     * 
     * @return list of Packages
     */
    public List<Package> findAll();

    /**
     * Returns all Packages' names.
     * 
     * @return list of names
     */
    public List<String> findAllNames();
}

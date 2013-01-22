package com.autoupdater.server.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.autoupdater.server.models.Package;
import com.autoupdater.server.models.Update;

/**
 * Service responsible for managing Updates.
 * 
 * @see com.autoupdater.server.models.Update
 */
@Repository
public interface UpdateService {
    /**
     * Persists Update.
     * 
     * @param update
     *            Update to persist
     * @throws IOException
     *             thrown if error occurs while trying to save file
     */
    public void persist(Update update) throws IOException;

    /**
     * Merges changes into existing Update.
     * 
     * @param update
     *            package to merge
     * @return merged Update
     */
    public Update merge(Update update);

    /**
     * Removes Update.
     * 
     * @param update
     *            Update to remove
     */
    public void remove(Update update);

    /**
     * Updates detached Update.
     * 
     * @param update
     *            Update to refresh
     */
    public void refresh(Update update);

    /**
     * Finds Update by its ID.
     * 
     * @param id
     *            Update's ID
     * @return Update if found, null otherwise
     */
    public Update findById(int id);

    /**
     * Returns all Updates.
     * 
     * @return list of Updates
     */
    public List<Update> findAll();

    /**
     * Returns newest packages of types development/release if there is any of
     * those types present in database.
     * 
     * @param _package
     *            package for which updates should be returned
     * @return list of updates
     */
    public List<Update> findNewestByPackage(Package _package);
}

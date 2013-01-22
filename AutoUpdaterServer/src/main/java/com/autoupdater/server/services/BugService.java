package com.autoupdater.server.services;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.autoupdater.server.models.Bug;

/**
 * Service responsible for managing Bugs.
 * 
 * @see com.autoupdater.server.models.Bug
 */
@Repository
public interface BugService {
    /**
     * Persists Bug.
     * 
     * @param bug
     *            Bug to persist
     */
    public void persist(Bug bug);

    /**
     * Merges changes into existing Bug.
     * 
     * @param bug
     *            Bug to merge
     * @return merged Bug
     */
    public Bug merge(Bug bug);

    /**
     * Removes Bug.
     * 
     * @param bug
     *            Bug to remove
     */
    public void remove(Bug bug);

    /**
     * Updates detached Bug.
     * 
     * @param bug
     *            Bug to refresh
     */
    public void refresh(Bug bug);

    /**
     * Finds Bug by its ID.
     * 
     * @param id
     *            Bug's ID
     * @return Bug if found, null otherwise
     */
    public Bug findById(int id);

    /**
     * Returns all Bugs.
     * 
     * @return list of Bugs
     */
    public List<Bug> findAll();
}

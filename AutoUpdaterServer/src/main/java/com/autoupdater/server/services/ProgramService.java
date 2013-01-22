package com.autoupdater.server.services;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.autoupdater.server.models.Program;

/**
 * Service responsible for managing Programs.
 * 
 * @see com.autoupdater.server.models.Program
 */
@Repository
public interface ProgramService {
    /**
     * Persists Program.
     * 
     * @param program
     *            Program to persist
     */
    public void persist(Program program);

    /**
     * Merges changes into existing Program.
     * 
     * @param program
     *            Program to merge
     * @return merged program
     */
    public Program merge(Program program);

    /**
     * Removes Program.
     * 
     * @param program
     *            Program to remove
     */
    public void remove(Program program);

    /**
     * Updates detached program.
     * 
     * @param program
     *            Program to refresh
     */
    public void refresh(Program program);

    /**
     * Finds Program by its ID.
     * 
     * @param id
     *            Program's ID
     * @return Program if found, null otherwise
     */
    public Program findById(int id);

    /**
     * Finds Program by its name.
     * 
     * @param name
     *            Program's name
     * @return Program if found, null otherwise
     */
    public Program findByName(String name);

    /**
     * Returns all Programs.
     * 
     * @return list of Programs
     */
    public List<Program> findAll();

    /**
     * Returns all Programs' names.
     * 
     * @return list of names
     */
    public List<String> findAllNames();
}

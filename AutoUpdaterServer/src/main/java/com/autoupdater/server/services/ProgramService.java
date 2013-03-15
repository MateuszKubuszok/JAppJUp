/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
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

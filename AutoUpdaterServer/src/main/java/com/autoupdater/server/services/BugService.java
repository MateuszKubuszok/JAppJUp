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

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

    /**
     * Whether there already is update with given version number and
     * developmentVersion status.
     * 
     * @param _package
     *            package to check
     * @param update
     *            update with versions to check
     * @return true if version is available for a package, false otherwise
     */
    public boolean checkIfVersionAvailableForPackage(Package _package, Update update);
}

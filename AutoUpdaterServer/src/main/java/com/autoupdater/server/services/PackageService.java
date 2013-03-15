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

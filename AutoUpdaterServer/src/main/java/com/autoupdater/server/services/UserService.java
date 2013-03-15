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

import com.autoupdater.server.models.User;

/**
 * Service responsible for managing Users.
 * 
 * @see com.autoupdater.server.models.User
 */
@Repository
public interface UserService {
    /**
     * Persists User.
     * 
     * @param user
     *            User to persist
     */
    public void persist(User user);

    /**
     * Merges changes into existing User.
     * 
     * @param user
     *            User to merge
     * @return merged User
     */
    public User merge(User user);

    /**
     * Removes User.
     * 
     * @param user
     *            User to remove
     */
    public void remove(User user);

    /**
     * Updates detached User.
     * 
     * @param user
     *            User to refresh
     */
    public void refresh(User user);

    /**
     * Finds User by its ID.
     * 
     * @param id
     *            User's ID
     * @return User if found, null otherwise
     */
    public User findById(int id);

    /**
     * Finds User by its username.
     * 
     * @param username
     *            User's name
     * @return User if found, null otherwise
     */
    public User findByUsername(String username);

    /**
     * Returns all Users.
     * 
     * @return list of Users
     */
    public List<User> findAll();

    /**
     * Returns all Usernames.
     * 
     * @return list of usernames
     */
    public List<String> findAllUsernames();
}

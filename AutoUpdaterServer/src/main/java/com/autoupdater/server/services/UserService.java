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

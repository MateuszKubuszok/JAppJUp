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

import org.apache.log4j.Logger;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autoupdater.server.models.User;

/**
 * Implementation of UserService
 * 
 * @see com.autoupdater.server.services.UserService
 */
@Service
@Transactional
public class UserServiceImp extends AbstractHibernateService implements UserService {
    /**
     * Service's logger.
     */
    private static Logger logger = Logger.getLogger(UserServiceImp.class);

    @Override
    public void persist(User user) {
        logger.debug("Attempting to persist User: " + user);
        getSession().persist(user);
        logger.debug("Persisted User: " + user);
    }

    @Override
    public User merge(User user) {
        logger.debug("Attempting to merge User: " + user);
        user = (User) getSession().merge(user);
        logger.debug("Merged User: " + user);
        return user;
    }

    @Override
    public void remove(User user) {
        logger.debug("Attempting to delete User: " + user);
        getSession().delete(user);
        logger.debug("Deleted User: " + user);
    }

    @Override
    public void refresh(User user) {
        logger.debug("Attempting to update User: " + user);
        getSession().update(user);
        logger.debug("Persisted Updated: " + user);
    }

    @Override
    public User findById(int id) {
        logger.debug("Attempting to find User by id: " + id);
        User user = (User) getSession().createCriteria(User.class).add(Restrictions.eq("id", id))
                .uniqueResult();
        logger.debug("Found User by id: " + id);
        return user;

    }

    @Override
    public User findByUsername(String username) {
        logger.debug("Attempting to find User by username: " + username);
        User user = (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("username", username)).uniqueResult();
        logger.debug("Found User by username: " + username);
        return user;

    }

    @Override
    public List<User> findAll() {
        logger.debug("Attempting to find all Users");
        List<User> users = getSession().createCriteria(User.class).list();
        logger.debug("Found all Users: " + users.size());
        return users;
    }

    @Override
    public List<String> findAllUsernames() {
        logger.debug("Attempting to find all Users' names");
        List<String> names = getSession().createCriteria(User.class)
                .setProjection(Projections.projectionList().add(Projections.property("username")))
                .list();
        logger.debug("Found all Users' names: " + names.size());
        return names;
    }
}

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
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autoupdater.server.models.Bug;

/**
 * Implementation of BugService.
 * 
 * @see com.autoupdater.server.services.BugService
 */
@Service
@Transactional
public class BugServiceImp extends AbstractHibernateService implements BugService {
    /**
     * Service's logger.
     */
    private static Logger logger = Logger.getLogger(BugServiceImp.class);

    @Override
    public void persist(Bug bug) {
        logger.debug("Attempting to persist Bug: " + bug);
        getSession().persist(bug);
        logger.debug("Persisted Bug: " + bug);
    }

    @Override
    public Bug merge(Bug bug) {
        logger.debug("Attempting to merge Bug: " + bug);
        bug = (Bug) getSession().merge(bug);
        logger.debug("Merged Bug: " + bug);
        return bug;
    }

    @Override
    public void remove(Bug bug) {
        logger.debug("Attempting to delete Bug: " + bug);
        getSession().delete(bug);
        logger.debug("Deleted Bug: " + bug);
    }

    @Override
    public void refresh(Bug bug) {
        logger.debug("Attempting to update Bug: " + bug);
        getSession().update(bug);
        logger.debug("Updated Bug: " + bug);
    }

    @Override
    public Bug findById(int id) {
        logger.debug("Attempting to find Bug by id: " + id);
        Bug bug = (Bug) getSession().createCriteria(Bug.class).add(Restrictions.eq("id", id))
                .uniqueResult();
        logger.debug("Found Bug, id: " + id);
        return bug;
    }

    @Override
    public List<Bug> findAll() {
        logger.debug("Attempting to find all Bugs");
        @SuppressWarnings({ "cast" })
        List<Bug> bugs = (List<Bug>) getSession().createCriteria(Bug.class).list();
        logger.debug("Found all Bugs: " + bugs.size());
        return bugs;
    }
}

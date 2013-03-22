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

import static org.apache.log4j.Logger.getLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autoupdater.server.models.Package;
import com.autoupdater.server.models.Update;

/**
 * Implementation of UpdateService.
 * 
 * @see com.autoupdater.server.services.UpdateService
 */
@Service
@Transactional
public class UpdateServiceImp extends AbstractHibernateService implements UpdateService {
    /**
     * Service's logger.
     */
    private final static Logger logger = getLogger(UpdateServiceImp.class);

    /**
     * Instance of FileService.
     */
    @Autowired
    private FileService fileService;

    @Override
    public void persist(Update update) throws IOException {
        logger.debug("Attempting to persist Update: " + update);
        update.setFileData(fileService.saveMultipartFile(update.getFile()));
        update.setFileName(update.getFile().getOriginalFilename());
        update.setFileSize(update.getFile().getSize());
        getSession().persist(update);
        logger.debug("Persisted Update: " + update);
    }

    @Override
    public Update merge(Update update) {
        logger.debug("Attempting to persist Update: " + update);
        update = (Update) getSession().merge(update);
        logger.debug("Merged Update: " + update);
        return update;
    }

    @Override
    public void remove(Update update) {
        logger.debug("Attempting to persist Update: " + update);
        String fileData = update.getFileData();
        getSession().delete(update);
        fileService.removeFile(fileData);
        logger.debug("Deleted Update: " + update);
    }

    @Override
    public void refresh(Update update) {
        logger.debug("Attempting to persist Update: " + update);
        getSession().update(update);
        logger.debug("Updated Update: " + update);
    }

    @Override
    public Update findById(int id) {
        logger.debug("Attempting to find Update by id: " + id);
        Update update = (Update) getSession().createCriteria(Update.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        logger.debug("Found Update by id: " + id);
        return update;
    }

    @Override
    public List<Update> findAll() {
        logger.debug("Attempting to find all Updates");
        @SuppressWarnings({ "cast" })
        List<Update> updates = (List<Update>) getSession().createCriteria(Update.class).list();
        logger.debug("Found all Updates: " + updates.size());
        return updates;
    }

    @Override
    public List<Update> findNewestByPackage(Package _package) {
        logger.debug("Attempting to find Updates for Package: " + _package);

        Update development = (Update) getSession().createCriteria(Update.class)
                .add(Restrictions.eq("thePackage", _package))
                .add(Restrictions.eq("developmentVersion", true)).addOrder(Order.desc("major"))
                .addOrder(Order.desc("minor")).addOrder(Order.desc("release"))
                .addOrder(Order.desc("nightly")).setMaxResults(1).uniqueResult();
        Update release = (Update) getSession().createCriteria(Update.class)
                .add(Restrictions.eq("thePackage", _package))
                .add(Restrictions.eq("developmentVersion", false)).addOrder(Order.desc("major"))
                .addOrder(Order.desc("minor")).addOrder(Order.desc("release"))
                .addOrder(Order.desc("nightly")).setMaxResults(1).uniqueResult();

        List<Update> updates = new ArrayList<Update>();
        if (development != null)
            updates.add(development);
        if (release != null)
            updates.add(release);

        logger.debug("Found Updates for Package: " + updates.size());
        return updates;
    }

    @Override
    public boolean checkIfVersionAvailableForPackage(Package _package, Update update) {
        logger.debug("Attempting to check version availability for Update: " + update
                + ", for Package: " + _package);

        boolean versionAvailable = getSession().createCriteria(Update.class)
                .add(Restrictions.eq("thePackage", _package))
                .add(Restrictions.eq("major", update.getMajor()))
                .add(Restrictions.eq("minor", update.getMinor()))
                .add(Restrictions.eq("release", update.getRelease()))
                .add(Restrictions.eq("nightly", update.getNightly()))
                .add(Restrictions.eq("developmentVersion", update.isDevelopmentVersion())).list()
                .isEmpty();

        logger.debug("Found version availability for Update: " + update + ", for Package: "
                + _package + ": " + versionAvailable);
        return versionAvailable;
    }
}

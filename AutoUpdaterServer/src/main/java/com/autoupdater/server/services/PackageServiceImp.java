package com.autoupdater.server.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autoupdater.server.models.Package;

/**
 * Implementation of PackageService.
 * 
 * @see com.autoupdater.server.services.PackageService
 */
@Service
@Transactional
public class PackageServiceImp extends AbstractHibernateService implements PackageService {
    /**
     * Service's logger.
     */
    private static Logger logger = Logger.getLogger(PackageServiceImp.class);

    @Override
    public void persist(Package _package) {
        logger.debug("Attempting to persist Package: " + _package);
        getSession().persist(_package);
        logger.debug("Persisted Package: " + _package);
    }

    @Override
    public Package merge(Package _package) {
        logger.debug("Attempting to merge Package: " + _package);
        _package = (Package) getSession().merge(_package);
        logger.debug("Merged Package: " + _package);
        return _package;
    }

    @Override
    public void remove(Package _package) {
        logger.debug("Attempting to delete Package: " + _package);
        getSession().delete(_package);
        logger.debug("Deleted Package: " + _package);
    }

    @Override
    public void refresh(Package _package) {
        logger.debug("Attempting to update Package: " + _package);
        getSession().update(_package);
        logger.debug("Updated Package: " + _package);
    }

    @Override
    public Package findById(int id) {
        logger.debug("Attempting to find Package by id: " + id);
        Package _package = (Package) getSession().createCriteria(Package.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        logger.debug("Found Package, id: " + id);
        return _package;
    }

    @Override
    public List<Package> findAll() {
        logger.debug("Attempting to find all Packages");
        @SuppressWarnings("unchecked")
        List<Package> packages = getSession().createCriteria(Package.class).list();
        logger.debug("Found all Packages: " + packages.size());
        return packages;
    }

    @Override
    public List<String> findAllNames() {
        logger.debug("Attempting to find all Packages' names");
        @SuppressWarnings({ "unchecked", "cast" })
        List<String> names = (List<String>) getSession().createCriteria(Package.class)
                .setProjection(Projections.projectionList().add(Projections.property("name")))
                .list();
        logger.debug("Found all Packages' names: " + names.size());
        return names;
    }
}

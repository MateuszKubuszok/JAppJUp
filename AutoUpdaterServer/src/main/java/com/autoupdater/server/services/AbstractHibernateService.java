package com.autoupdater.server.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract service that autowires SessionFactory and supplies sessions for
 * service.
 */
public abstract class AbstractHibernateService {
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Returns new Session instance.
     * 
     * @return new Session
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}

package com.autoupdater.server.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autoupdater.server.models.Program;

/**
 * Implementation of ProgramService.
 * 
 * @see com.autoupdater.server.services.ProgramService
 */
@Service
@Transactional
public class ProgramServiceImp extends AbstractHibernateService implements ProgramService {
	/**
     * Service's logger.
     */
    private static Logger logger = Logger.getLogger(ProgramServiceImp.class);
	
    @Override
    public void persist(Program program) {
    	logger.debug("Attempting to persist Program: " + program);
        getSession().persist(program);
        logger.debug("Persisted Program: " + program);
    }

    @Override
    public Program merge(Program program) {
    	logger.debug("Attempting to merge Program: " + program);
        program = (Program) getSession().merge(program);
        logger.debug("Merged Program: " + program);
        return program;
    }

    @Override
    public void remove(Program program) {
    	logger.debug("Attempting to delete Program: " + program);
        getSession().delete(program);
        logger.debug("Deleted Program: " + program);
    }

    @Override
    public void refresh(Program program) {
    	logger.debug("Attempting to update Program: " + program);
        getSession().update(program);
        logger.debug("Updated Program: " + program);
    }

    @Override
    public Program findById(int id) {
    	logger.debug("Attempting to find Program by id: " + id);
        Program program = (Program) getSession().createCriteria(Program.class).add(Restrictions.eq("id", id))
                .uniqueResult();
        logger.debug("Found Program by id: " + id);
        return program;
    }

    @Override
    public Program findByName(String name) {
    	logger.debug("Attempting to find Program by name: " + name);
        Program program = (Program) getSession().createCriteria(Program.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
        logger.debug("Found Program by name: " + name);
        return program;
    }

    @Override
    public List<Program> findAll() {
    	logger.debug("Attempting to find all Programs");
    	@SuppressWarnings("unchecked")
        List<Program> programs = getSession().createCriteria(Program.class).list();
    	logger.debug("Found all Programs: " + programs.size());
        return programs;
    }

    @Override
    public List<String> findAllNames() {
    	logger.debug("Attempting to find all Programs' names");
    	@SuppressWarnings("unchecked")
        List<String> names = getSession().createCriteria(Program.class)
                .setProjection(Projections.projectionList().add(Projections.property("name")))
                .list();
    	logger.debug("Found all Programs' names: " + names.size());
        return names;
    }
}

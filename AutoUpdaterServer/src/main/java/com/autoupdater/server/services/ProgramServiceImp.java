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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autoupdater.server.models.Bug;
import com.autoupdater.server.models.Package;
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

    /**
     * Instance of PackageService.
     */
    @Autowired
    private PackageService packageService;

    /**
     * Instance of BugService.
     */
    @Autowired
    private BugService bugService;

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
        for (Package _package : program.getPackages())
            packageService.remove(_package);
        for (Bug bug : program.getBugs())
            bugService.remove(bug);
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
        Program program = (Program) getSession().createCriteria(Program.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
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
        List<Program> programs = getSession().createCriteria(Program.class).list();
        logger.debug("Found all Programs: " + programs.size());
        return programs;
    }

    @Override
    public List<String> findAllNames() {
        logger.debug("Attempting to find all Programs' names");
        List<String> names = getSession().createCriteria(Program.class)
                .setProjection(Projections.projectionList().add(Projections.property("name")))
                .list();
        logger.debug("Found all Programs' names: " + names.size());
        return names;
    }
}

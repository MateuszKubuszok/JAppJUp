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
package com.autoupdater.server.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.autoupdater.server.constraints.UniqueProgramName;
import com.autoupdater.server.xml.models.ProgramXML;

/**
 * Model of Program, used to store information about programs in repository.
 * 
 * @see com.autoupdater.server.services.ProgramService
 */
@Entity
@Table(name = "programs")
@UniqueProgramName
public class Program {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name", unique = true)
    @NotNull
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "program", orphanRemoval = true)
    private List<Package> packages;

    @OneToMany(mappedBy = "program", orphanRemoval = true)
    private List<Bug> bugs;

    public Program() {
        packages = new ArrayList<Package>();
        bugs = new ArrayList<Bug>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }

    @Transient
    public ProgramXML getProgramPackage() {
        return new ProgramXML(getName(), getPackages());
    }

    public boolean containsPackageWithName(String name) {
        if (packages == null || packages.isEmpty())
            return false;
        for (Package _package : packages)
            if (_package.getName() != null && _package.getName().equals(name))
                return true;
        return false;
    }

    public Package getPackageWithName(String name) {
        if (packages == null || packages.isEmpty())
            return null;
        for (Package _package : packages)
            if (_package.getName() != null && _package.getName().equals(name))
                return _package;
        return null;
    }
}

package com.autoupdater.server.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.autoupdater.server.constraints.UniquePackageName;
import com.autoupdater.server.xml.models.ChangelogXML;

/**
 * Model of Package, used to store information about Programs' Packages.
 * 
 * @see com.autoupdater.server.services.PackageService
 */
@Entity
@Table(name = "packages")
@UniquePackageName
public class Package {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name")
    @NotNull
    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name = "program")
    @NotNull
    private Program program;

    @OneToMany(mappedBy = "thePackage", orphanRemoval = true)
    private List<Update> updates;

    public Package() {
        updates = new ArrayList<Update>();
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

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public List<Update> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Update> updates) {
        this.updates = updates;
    }

    @Transient
    public List<ChangelogXML> getChangelog() {
        List<ChangelogXML> changelog = new ArrayList<ChangelogXML>();
        if (updates != null)
            for (Update update : updates)
                changelog.add(new ChangelogXML(update.getChangelog(), update.getVersion()));
        return changelog;
    }
}

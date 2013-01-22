package com.autoupdater.server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Model of Bug, used to store information about Program's known bugs.
 * 
 * @see com.autoupdater.server.services.BugService
 */
@Entity
@Table(name = "bugs")
public class Bug {
    @Column(name = "id")
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "description")
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "program")
    @NotNull
    private Program program;

    public Bug() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}

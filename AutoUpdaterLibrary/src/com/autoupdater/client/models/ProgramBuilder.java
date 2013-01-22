package com.autoupdater.client.models;

import java.util.SortedSet;

/**
 * Builder that creates Program instances.
 * 
 * @see com.autoupdater.client.models.Program
 */
public class ProgramBuilder {
    private final Program program;

    private ProgramBuilder() {
        program = new Program();
    }

    /**
     * Creates new ProgramBuilder.
     * 
     * @return ProgramBuilder
     */
    public static ProgramBuilder builder() {
        return new ProgramBuilder();
    }

    public ProgramBuilder setName(String name) {
        program.setName(name);
        return this;
    }

    public ProgramBuilder setPathToProgramDirectory(String pathToProgramDirectory) {
        program.setPathToProgramDirectory(pathToProgramDirectory);
        return this;
    }

    public ProgramBuilder setServerAddress(String serverAddress) {
        program.setServerAddress(serverAddress);
        return this;
    }

    public ProgramBuilder setPackages(SortedSet<Package> packages) {
        program.setPackages(packages);
        return this;
    }

    public ProgramBuilder setBugs(SortedSet<BugEntry> bugs) {
        program.setBugs(bugs);
        return this;
    }

    public ProgramBuilder setDevelopmentVersion(boolean developmentVersion) {
        program.setDevelopmentVersion(developmentVersion);
        return this;
    }

    public ProgramBuilder setDevelopmentVersion(String developmentVersion) {
        program.setDevelopmentVersion(developmentVersion);
        return this;
    }

    /**
     * Builds Program.
     * 
     * @return Program
     */
    public Program build() {
        return program;
    }
}

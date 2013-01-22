package com.autoupdater.server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.autoupdater.server.constraints.FileAttatched;
import com.autoupdater.server.constraints.UpdaterCommandDefined;
import com.autoupdater.server.constraints.VersionNumberCorrect;

/**
 * Model of Update, used to store information about Packages' Updates.
 * 
 * @see com.autoupdater.server.services.UpdateService
 */
@Entity
@Table(name = "updates")
@FileAttatched
@UpdaterCommandDefined
@VersionNumberCorrect
public class Update {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "package")
    private Package thePackage;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "uploader")
    private User uploader;

    @Column(name = "changelog")
    @NotNull
    @NotEmpty
    private String changelog;

    @Column(name = "major")
    private int major;

    @Column(name = "minor")
    private int minor;

    @Column(name = "_release")
    private int release;

    @Column(name = "nightly")
    private int nightly;

    @Column(name = "developmentVersion")
    private boolean developmentVersion;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private EUpdateStrategy type;

    @Column(name = "fileData")
    private String fileData;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileSize")
    private long fileSize;

    @Column(name = "fileType")
    private String fileType;

    @Column(name = "relativePath")
    private String relativePath;

    @Column(name = "updaterCommand")
    private String updaterCommand;

    @Transient
    private CommonsMultipartFile file;

    public Update() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Package getThePackage() {
        return thePackage;
    }

    public void setThePackage(Package _package) {
        this.thePackage = _package;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public boolean isDevelopmentVersion() {
        return developmentVersion;
    }

    public void setDevelopmentVersion(boolean developmentVersion) {
        this.developmentVersion = developmentVersion;
    }

    @Transient
    public String getVersion() {
        return major + "." + minor + "." + release + "." + nightly;
    }

    public void setVersion(String version) {
        major = minor = release = nightly = 0;
        String temp[] = version.split("\\.");
        try {
            if (temp.length >= 1)
                major = Integer.parseInt(temp[0]);
            if (temp.length >= 2)
                minor = Integer.parseInt(temp[1]);
            if (temp.length >= 3)
                release = Integer.parseInt(temp[2]);
            if (temp.length >= 4)
                nightly = Integer.parseInt(temp[3]);
        } catch (NumberFormatException e) {
        }
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Transient
    public String getUpdateType() {
        return type != null ? type.getMessage() : null;
    }

    public void setUpdateType(String message) {
        type = EUpdateStrategy.parse(message);
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getRelease() {
        return major;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public int getNightly() {
        return nightly;
    }

    public void setNightly(int nightly) {
        this.nightly = nightly;
    }

    public EUpdateStrategy getType() {
        return type;
    }

    public void setType(EUpdateStrategy type) {
        this.type = type;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getUpdaterCommand() {
        return updaterCommand;
    }

    public void setUpdaterCommand(String updaterCommand) {
        this.updaterCommand = updaterCommand;
    }

    @Transient
    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
        this.fileType = file.getContentType();
    }
}

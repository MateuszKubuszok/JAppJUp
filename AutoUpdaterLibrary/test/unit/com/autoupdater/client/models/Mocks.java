package com.autoupdater.client.models;

import com.autoupdater.client.Values;

public class Mocks {
    public static Update update() {
        return UpdateBuilder.builder().setID("1").setPackageName(Values.Update.packageName)
                .setPackageID(Values.Update.packageID).setVersionNumber(Values.Update.version)
                .setDevelopmentVersion(Values.Update.developmentVersion)
                .setChanges(Values.Update.changelog).setUpdateStrategy(Values.Update.type)
                .setRelativePath(Values.Update.relativePath)
                .setOriginalName(Values.Update.originalName)
                .setCommand(Values.Update.updaterCommand).build();
    }

    public static Package _package() {
        return PackageBuilder.builder().setName(Values.Package.name)
                .setVersionNumber(Values.Package.version).setID(Values.Package.ID)
                .setUpdate(update()).build();
    }
}

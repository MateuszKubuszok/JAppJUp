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
package com.autoupdater.client.models;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.Paths;
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

    public static SortedSet<Update> updates() {
        SortedSet<Update> updates = new TreeSet<Update>();
        updates.add(update());
        return updates;
    }

    public static Package _package() {
        return PackageBuilder.builder().setName(Values.Package.name)
                .setVersionNumber(Values.Package.version).setID(Values.Package.ID)
                .setUpdates(updates()).build();
    }

    public static SortedSet<Package> packages() {
        SortedSet<Package> packages = new TreeSet<Package>();
        packages.add(_package());
        return packages;
    }

    public static Program program() {
        return ProgramBuilder.builder().setName(Values.Program.name)
                .setServerAddress(Values.Program.serverAddress)
                .setPathToProgramDirectory(Paths.Installations.Program.programDir)
                .setPackages(packages()).build();
    }

    public static SortedSet<Program> programs() {
        return new TreeSet<Program>(Arrays.asList(program()));
    }
}

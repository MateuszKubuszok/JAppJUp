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

import static java.util.Arrays.asList;

import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.Paths;
import com.autoupdater.client.Values;

public class MockModels {
    public static Update update() {
        return UpdateBuilder.builder().setID("1").setPackageName(Values.Update.packageName)
                .setPackageID(Values.Update.packageID).setVersionNumber(Values.Update.version)
                .setDevelopmentVersion(Values.Update.developmentVersion)
                .setChanges(Values.Update.changelog).setUpdateStrategy(Values.Update.type)
                .setRelativePath(Values.Update.relativePath)
                .setOriginalName(Values.Update.originalName)
                .setCommand(Values.Update.updaterCommand).build();
    }

    public static Update update2() {
        return UpdateBuilder.builder().setID("2").setPackageName(Values.Update2.packageName)
                .setPackageID(Values.Update2.packageID).setVersionNumber(Values.Update2.version)
                .setDevelopmentVersion(Values.Update2.developmentVersion)
                .setChanges(Values.Update2.changelog).setUpdateStrategy(Values.Update2.type)
                .setRelativePath(Values.Update2.relativePath)
                .setOriginalName(Values.Update2.originalName)
                .setCommand(Values.Update2.updaterCommand).build();
    }

    public static SortedSet<Update> updates() {
        return new TreeSet<Update>(asList(update(), update2()));
    }

    public static Package _package() {
        return PackageBuilder.builder().setName(Values.Package.name)
                .setVersionNumber(Values.Package.version).setID(Values.Package.ID)
                .setUpdates(new TreeSet<Update>(asList(update()))).build();
    }

    public static Package _package2() {
        return PackageBuilder.builder().setName(Values.Package2.name)
                .setVersionNumber(Values.Package2.version).setID(Values.Package2.ID)
                .setUpdates(new TreeSet<Update>(asList(update2()))).build();
    }

    public static SortedSet<Package> packages() {
        return new TreeSet<Package>(asList(_package(), _package2()));
    }

    public static Program program() {
        return ProgramBuilder.builder().setName(Values.Program.name)
                .setServerAddress(Values.Program.serverAddress)
                .setPathToProgramDirectory(Paths.Installations.Program.programDir)
                .setPackages(packages()).build();
    }

    public static SortedSet<Program> programs() {
        return new TreeSet<Program>(asList(program()));
    }
}

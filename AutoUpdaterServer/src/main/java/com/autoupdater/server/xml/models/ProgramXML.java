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
package com.autoupdater.server.xml.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.autoupdater.server.models.Package;
import com.autoupdater.server.models.Program;

@XmlRootElement(name = "program")
public class ProgramXML {
    private final String name;

    private final List<PackageXML> packages;

    public ProgramXML() {
        name = "";
        packages = new ArrayList<PackageXML>();
    }

    public ProgramXML(String name, List<Package> packages) {
        this.name = name;
        this.packages = new ArrayList<PackageXML>();
        for (Package _package : packages)
            this.packages.add(new PackageXML(_package));
    }

    public ProgramXML(Program program) {
        this.name = program.getName();
        this.packages = new ArrayList<PackageXML>();
        for (Package _package : program.getPackages())
            this.packages.add(new PackageXML(_package));
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "package")
    public List<PackageXML> getPackages() {
        return packages;
    }
}

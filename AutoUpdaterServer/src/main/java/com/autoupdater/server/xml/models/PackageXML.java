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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.autoupdater.server.models.Package;

@XmlRootElement(name = "package")
public class PackageXML {
    private final int id;

    private final String name;

    public PackageXML() {
        id = 0;
        name = "";
    }

    public PackageXML(Package _package) {
        id = _package.getId();
        name = _package.getName();
    }

    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }
}

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
import javax.xml.bind.annotation.XmlValue;

import com.autoupdater.server.models.Bug;

@XmlRootElement(name = "bug")
public class BugXML {
    private final int programId;
    private final String description;

    public BugXML() {
        programId = 0;
        description = "";
    }

    public BugXML(Bug bug) {
        programId = bug.getProgram().getId();
        description = bug.getDescription();
    }

    @XmlAttribute(name = "programID")
    public int getProgramId() {
        return programId;
    }

    @XmlValue
    public String getDescription() {
        return description;
    }
}

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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.autoupdater.server.models.Update;

@XmlRootElement(name = "changelogs")
public class ChangelogsXML {
    private final List<ChangelogXML> changelogs;

    public ChangelogsXML() {
        changelogs = new ArrayList<ChangelogXML>();
    }

    public ChangelogsXML(List<Update> updates) {
        changelogs = new ArrayList<ChangelogXML>();
        if (updates != null)
            for (Update update : updates)
                changelogs.add(new ChangelogXML(update));
    }

    @XmlElement(name = "changelog")
    public List<ChangelogXML> getChangelogs() {
        return changelogs;
    }
}

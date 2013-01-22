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

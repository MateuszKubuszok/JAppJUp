package com.autoupdater.server.xml.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.autoupdater.server.models.Update;

@XmlRootElement(name = "updates")
public class UpdatesXML {
    private final List<UpdateXML> updates;

    public UpdatesXML() {
        updates = new ArrayList<UpdateXML>();
    }

    public UpdatesXML(List<Update> updates) {
        this.updates = new ArrayList<UpdateXML>();
        if (updates != null)
            for (Update update : updates)
                this.updates.add(new UpdateXML(update));
    }

    @XmlElement(name = "update")
    public List<UpdateXML> getUpdates() {
        return updates;
    }
}

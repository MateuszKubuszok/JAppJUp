package com.autoupdater.client.xml.parsers;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.autoupdater.client.models.Update;
import com.autoupdater.client.models.UpdateBuilder;
import com.autoupdater.client.xml.schema.UpdateInfoSchema;

/**
 * Implementation parsing incoming XML data into Update model.
 */
public class UpdateInfoParser extends AbstractXMLParser<SortedSet<Update>> {
    @Override
    public SortedSet<Update> parseDocument(Document document) throws ParserException {
        try {
            SortedSet<Update> updates = new TreeSet<Update>();

            List<? extends Node> programsNode = document.selectNodes("./"
                    + UpdateInfoSchema.Updates.update_);

            for (Node updateNode : programsNode) {
                String id = ((Element) updateNode)
                        .attributeValue(UpdateInfoSchema.Updates.Update.id);
                String packageName = ((Element) updateNode)
                        .attributeValue(UpdateInfoSchema.Updates.Update.packageName);
                String packageId = ((Element) updateNode)
                        .attributeValue(UpdateInfoSchema.Updates.Update.packageID);
                String version = ((Element) updateNode)
                        .attributeValue(UpdateInfoSchema.Updates.Update.version);
                String developmentVersion = ((Element) updateNode)
                        .attributeValue(UpdateInfoSchema.Updates.Update.developmentVersion);
                String updateStrategy = ((Element) updateNode)
                        .attributeValue(UpdateInfoSchema.Updates.Update.type);
                String originalName = ((Element) updateNode)
                        .attributeValue(UpdateInfoSchema.Updates.Update.originalName);
                String relativePath = ((Element) updateNode)
                        .attributeValue(UpdateInfoSchema.Updates.Update.relativePath);
                String command = ((Element) updateNode)
                        .attributeValue(UpdateInfoSchema.Updates.Update.command);
                String changelog = getContent((Element) updateNode);

                updates.add(UpdateBuilder.builder().setID(id).setPackageName(packageName)
                        .setPackageID(packageId).setVersionNumber(version)
                        .setDevelopmentVersion(developmentVersion).setChanges(changelog)
                        .setUpdateStrategy(updateStrategy).setOriginalName(originalName)
                        .setRelativePath(relativePath).setCommand(command).build());
            }

            return updates;
        } catch (Exception e) {
            throw new ParserException("Error occured while parsing response");
        }
    }
}

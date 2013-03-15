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
package com.autoupdater.client.download.runnables.post.download.strategies;

import java.util.SortedSet;

import com.autoupdater.client.models.ChangelogEntry;
import com.autoupdater.client.xml.parsers.AbstractXMLParser;
import com.autoupdater.client.xml.parsers.ChangelogInfoParser;

/**
 * Implementation of DownloadStorageStrategyInterface used for parsing results
 * into Changelogs.
 * 
 * @see com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.AbstractXMLPostDownloadStrategy
 */
public class ChangelogInfoPostDownloadStrategy extends
        AbstractXMLPostDownloadStrategy<SortedSet<ChangelogEntry>> {
    @Override
    protected AbstractXMLParser<SortedSet<ChangelogEntry>> getParser() {
        return new ChangelogInfoParser();
    }
}
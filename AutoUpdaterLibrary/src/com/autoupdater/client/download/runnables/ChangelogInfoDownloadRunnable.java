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
package com.autoupdater.client.download.runnables;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import com.autoupdater.client.download.runnables.post.download.strategies.ChangelogInfoPostDownloadStrategy;
import com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy;
import com.autoupdater.client.models.ChangelogEntry;

/**
 * Implementation downloading changelogs.
 * 
 * <p>
 * Use ChangelogInfoDownloadStrategy.
 * </p>
 * 
 * <p>
 * Used by ChangelogInfoDownloadService.
 * </p>
 * 
 * @see com.autoupdater.client.download.runnables.AbstractDownloadRunnable
 * @see com.autoupdater.client.download.runnables.post.download.strategies.ChangelogInfoPostDownloadStrategy
 * @see com.autoupdater.client.download.services.ChangelogInfoDownloadService
 */
public class ChangelogInfoDownloadRunnable extends
        AbstractDownloadRunnable<SortedSet<ChangelogEntry>> {
    /**
     * Creates ChangelogInfoDownloadRunnable instance.
     * 
     * @param connection
     *            connection used for obtaining data
     */
    public ChangelogInfoDownloadRunnable(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected IPostDownloadStrategy<SortedSet<ChangelogEntry>> getPostDownloadStrategy() {
        return new ChangelogInfoPostDownloadStrategy();
    }
}

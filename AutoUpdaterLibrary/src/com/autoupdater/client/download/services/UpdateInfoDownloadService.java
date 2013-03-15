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
package com.autoupdater.client.download.services;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.download.runnables.UpdateInfoDownloadRunnable;
import com.autoupdater.client.models.Update;

/**
 * Service, that download update info from given connection, and returns result
 * as Update model.
 */
public class UpdateInfoDownloadService extends AbstractDownloadService<SortedSet<Update>> {
    /**
     * Creates instance of UpdateInfoDownloadService.
     * 
     * @param connection
     *            connection used to obtain data
     */
    public UpdateInfoDownloadService(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected AbstractDownloadRunnable<SortedSet<Update>> getRunnable() {
        return new UpdateInfoDownloadRunnable(getConnection());
    }
}

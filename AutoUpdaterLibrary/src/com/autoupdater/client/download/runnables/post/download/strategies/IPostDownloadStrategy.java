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

import java.io.IOException;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.xml.parsers.ParserException;

/**
 * Interface describing details of DownloadStrategies.
 * 
 * <p>
 * Used by DownloadRunnables to process raw download results into expected
 * format.
 * </p>
 * 
 * @see com.autoupdater.client.download.runnables.post.download.strategies.AbstractXMLPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.PackagesInfoPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.UpdateInfoPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.ChangelogInfoPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.FilePostDownloadStrategy
 * 
 * @param <Result>
 *            type of result returned by processor
 */
public interface IPostDownloadStrategy<Result> {
    /**
     * Used to pass content from InputStream into processor.
     * 
     * @param buffer
     *            buffer with currently passed data
     * @param readSize
     *            amount of currently passed bytes
     * @throws IOException
     *             thrown if error occurs while storing data into file (used in
     *             some implementations)
     */
    public void write(byte[] buffer, int readSize) throws IOException;

    /**
     * Returns processed result.
     * 
     * @return processed result
     * @throws ParserException
     *             thrown when result is parsed and error occurs in the process
     * @throws DownloadResultException
     *             thrown when trying to obtain result prematurely
     */
    public Result processDownload() throws ParserException, DownloadResultException;
}

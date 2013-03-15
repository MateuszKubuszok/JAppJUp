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

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import com.autoupdater.client.download.ConnectionConfiguration;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.xml.parsers.AbstractXMLParser;
import com.autoupdater.client.xml.parsers.ParserException;

/**
 * Superclass of all XMLDownloadStrategies. Implements
 * DownloadStorageStrategyInterface.
 * 
 * @see com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.PackagesInfoPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.UpdateInfoPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.ChangelogInfoPostDownloadStrategy
 * 
 * @param <Result>
 *            type of result returned by parser
 */
public abstract class AbstractXMLPostDownloadStrategy<Result> implements
        IPostDownloadStrategy<Result> {
    private final ByteArrayOutputStream out;

    public AbstractXMLPostDownloadStrategy() {
        out = new ByteArrayOutputStream();
    }

    @Override
    public void write(byte[] buffer, int readSize) {
        out.write(buffer, 0, readSize);
    }

    @Override
    public Result processDownload() throws DownloadResultException, ParserException {
        try {
            return getParser().parseXML(getXml());
        } catch (UnsupportedEncodingException e) {
            throw new DownloadResultException(e.getMessage()).addSuppresed(e,
                    DownloadResultException.class);
        }
    }

    /**
     * Converts result to String using required encoding.
     * 
     * @return XML as String
     * @throws UnsupportedEncodingException
     *             thrown if set encoding is not supported
     */
    String getXml() throws UnsupportedEncodingException {
        return out.toString(ConnectionConfiguration.XML_ENCODING_NAME);
    }

    /**
     * Returns parser used for parsing download results into required format.
     * 
     * @return parser instance
     */
    abstract protected AbstractXMLParser<Result> getParser();
}

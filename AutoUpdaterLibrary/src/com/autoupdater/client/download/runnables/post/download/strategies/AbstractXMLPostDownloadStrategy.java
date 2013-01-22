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
            throw new DownloadResultException(e.getMessage());
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

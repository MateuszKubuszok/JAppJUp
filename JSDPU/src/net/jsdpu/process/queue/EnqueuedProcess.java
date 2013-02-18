package net.jsdpu.process.queue;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Representation of enqueued process. Since it isn't represented as an actual
 * Process in current VM, but exists in remotely run child VM, it has to be
 * controlled via wrapper.
 * 
 * @TODO rewrite it into actual class
 */
public interface EnqueuedProcess {
    /**
     * @see java.lang.Process#destroy()
     */
    public void destroy();

    /**
     * @see java.lang.Process#exitValue()
     */
    public int getExitValue();

    /**
     * @see java.lang.Process#getInputStream()
     */
    public InputStream getInputStream();

    /**
     * @see java.lang.Process#getErrorStream()
     */
    public InputStream getErrorStream();

    /**
     * @see java.lang.Process#getOutputStream()
     */
    public OutputStream getOutputStream();

    /**
     * @see java.lang.Process#waitFor()
     */
    public int waitFor();
}

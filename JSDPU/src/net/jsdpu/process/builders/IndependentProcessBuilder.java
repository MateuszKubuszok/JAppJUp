package net.jsdpu.process.builders;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * This class is used to create operating system independent processes.
 * 
 * <p>
 * It uses ProcessBuilder for creating process, but adds its own functionality
 * to ensure that process is not run as Java VM's child process.
 * </p>
 * 
 * <p>
 * Because created process is completely independent from Java VM, it cannot be
 * killed by it, and no IO stream is available.
 * </p>
 * 
 * @see java.lang.ProcessBuilder
 */
public interface IndependentProcessBuilder {
    /**
     * @see java.lang.ProcessBuilder#command()
     */
    public List<String> getCommand();

    /**
     * @see java.lang.ProcessBuilder#command(List)
     */
    public IndependentProcessBuilder setCommand(List<String> command);

    /**
     * @see java.lang.ProcessBuilder#command(String...)
     */
    public IndependentProcessBuilder setCommand(String... command);

    /**
     * @see java.lang.ProcessBuilder#directory()
     */
    public File getDirectory();

    /**
     * @see java.lang.ProcessBuilder#directory(File)
     */
    public IndependentProcessBuilder setDirectory(File directory);

    /**
     * @see java.lang.ProcessBuilder#environment()
     */
    public Map<String, String> getEnvironment();

    /**
     * @see java.lang.ProcessBuilder#start()
     */
    public void start();
}

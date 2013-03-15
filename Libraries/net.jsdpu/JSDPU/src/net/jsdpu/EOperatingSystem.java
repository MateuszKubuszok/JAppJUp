/**
 * Copyright 2012-2013 Mateusz Kubuszok
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
package net.jsdpu;

import net.jsdpu.process.executors.AbstractProcessExecutor;
import net.jsdpu.process.executors.IProcessExecutor;
import net.jsdpu.process.executors.LinuxProcessExecutor;
import net.jsdpu.process.executors.MacOSProcessExecutor;
import net.jsdpu.process.executors.WindowsProcessExecutor;
import net.jsdpu.process.killers.IProcessKiller;
import net.jsdpu.process.killers.LinuxProcessKiller;
import net.jsdpu.process.killers.MacOSProcessKiller;
import net.jsdpu.process.killers.WindowsProcessKiller;

/**
 * Defines currently used operating system, as well as classes performing some
 * system-dependent operations.
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 * @see net.jsdpu.process.killers.IProcessKiller
 */
public enum EOperatingSystem {
    /**
     * Defines system dependent instances used on Windows family systems.
     * 
     * @see net.jsdpu.OperatingSystemConfiguration#WINDOWS_LOCAL_APP_DATA
     * @see net.jsdpu.process.executors.WindowsProcessExecutor
     * @see net.jsdpu.process.killers.WindowsProcessKiller
     */
    WINDOWS("Windows", OperatingSystemConfiguration.WINDOWS_LOCAL_APP_DATA,
            new WindowsProcessExecutor(), new WindowsProcessKiller(), "tasklist"),

    /**
     * Defines system dependent instances used on Linux family systems.
     * 
     * @see net.jsdpu.OperatingSystemConfiguration#LINUX_LOCAL_APP_DATA
     * @see net.jsdpu.process.executors.LinuxProcessExecutor
     * @see net.jsdpu.process.killers.LinuxProcessKiller
     */
    LINUX("Linux", OperatingSystemConfiguration.LINUX_LOCAL_APP_DATA, new LinuxProcessExecutor(),
            new LinuxProcessKiller(), "echo \"content\""),

    /**
     * Defines system dependent instances used on MacOS family systems.
     * 
     * @see net.jsdpu.OperatingSystemConfiguration#MAC_OS_LOCAL_APP_DATA
     * @see net.jsdpu.process.executors.MacOSProcessExecutor
     * @see net.jsdpu.process.killers.MacOSProcessKiller
     */
    MAC_OS("MacOS", OperatingSystemConfiguration.MAC_OS_LOCAL_APP_DATA, new MacOSProcessExecutor(),
            new MacOSProcessKiller(), null);

    private final String familyName;
    private final String localAppData;
    private final IProcessExecutor processExecutor;
    private final IProcessKiller processKiller;
    private final String testCommand;

    private static final EOperatingSystem current;
    static {
        EOperatingSystem eos = null;
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN"))
            eos = EOperatingSystem.WINDOWS;
        else if (OS.contains("MAC"))
            eos = EOperatingSystem.MAC_OS;
        else if (OS.contains("NUX"))
            eos = EOperatingSystem.LINUX;
        current = eos;
    }

    /**
     * Creates new EOperatingSystem instance.
     * 
     * @param familyName
     *            name of operating system family
     * @param localAppData
     *            location of "local application data" catalog
     * @param processExecutor
     *            instance of ProcessExecutor
     * @param processKiller
     *            instance of ProcessKiller
     * @param testCommand
     *            command used to tests on given system
     */
    private EOperatingSystem(String familyName, String localAppData,
            AbstractProcessExecutor processExecutor, IProcessKiller processKiller,
            String testCommand) {
        this.familyName = familyName;
        this.localAppData = localAppData;
        this.processExecutor = processExecutor;
        this.processKiller = processKiller;
        this.testCommand = testCommand;
    }

    /**
     * Returns family name for current operating system.
     * 
     * @return family name
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Returns path to local application data.
     * 
     * @return path to local application data
     */
    public String getLocalAppData() {
        return localAppData;
    }

    /**
     * Returns instance of ProcessExecutor suitable for current system.
     * 
     * @see net.jsdpu.process.executors.IProcessExecutor
     * 
     * @return ProcessExecutor
     */
    public IProcessExecutor getProcessExecutor() {
        return processExecutor;
    }

    /**
     * Returns instance of ProcessKiller suitable for current system.
     * 
     * @see net.jsdpu.process.killers.IProcessKiller
     * 
     * @return ProcessKiller
     */
    public IProcessKiller getProcessKiller() {
        return processKiller;
    }

    /**
     * Command that tests whether or not Executor works.
     * 
     * <p>
     * Since available commands are system-dependent it has to be defined here.
     * </p>
     * 
     * @return commands that tests executor under given system
     */
    public String getTestCommand() {
        return testCommand;
    }

    @Override
    public String toString() {
        return familyName;
    }

    /**
     * Returns EOperatingSustem for current operating system.
     * 
     * @return current operating system
     */
    public static EOperatingSystem current() {
        return current;
    }
}

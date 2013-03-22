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
package net.jsdpu.process.elevated;

import static java.lang.Integer.valueOf;
import static java.lang.System.getProperty;
import static net.jsdpu.resources.Resources.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ProcessBuilder allowing creation of an elevated subprocess on Windows.
 * 
 * @see net.jsdpu.process.elevated.AbstractElevatedProcessBuilder
 */
public class WindowsElevatedProcessBuilder extends AbstractElevatedProcessBuilder {
    /**
     * Lowest major version of Windows that require use of UAC handling.
     * 
     * <p>
     * Vista - first to use it was 6.0, 7 was 6.1 - thus checking whether major
     * version is greater or equal to 6 is sufficient.
     * </p>
     */
    private final int windowsVistaMajorVersion = 6;

    @Override
    public ProcessBuilder getProcessBuilder() {
        if (isVistaOrLater()) {
            List<String> windowsElevationCommand = new ArrayList<String>();
            windowsElevationCommand.add(getUACHandlerPath());
            windowsElevationCommand.addAll(getCommand());
            getBuilder().command(windowsElevationCommand);
        } else
            getBuilder().command(getCommand());
        return getBuilder();
    }

    /**
     * Returns whether current system is Windows Vista or newer.
     * 
     * @return true is Vista or later, false otherwise
     */
    protected boolean isVistaOrLater() {
        String major = getProperty("os.version").split("\\.")[0];
        try {
            return valueOf(major) >= windowsVistaMajorVersion || true;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    @Override
    protected void finalize() {
        uninstallWindowsWrapper();
    }
}

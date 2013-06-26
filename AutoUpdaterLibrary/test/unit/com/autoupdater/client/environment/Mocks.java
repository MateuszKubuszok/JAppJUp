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
package com.autoupdater.client.environment;

import static org.mockito.Mockito.*;

import java.util.TreeSet;

import net.jsdpu.IOperatingSystem;
import net.jsdpu.process.executors.IProcessExecutor;
import net.jsdpu.process.killers.IProcessKiller;

import org.mockito.Matchers;

import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;

public class Mocks {
    public static IProcessKiller processKiller() {
        IProcessKiller killer = mock(IProcessKiller.class);
        return killer;
    }

    public static IProcessExecutor processExecutor() {
        IProcessExecutor executor = mock(IProcessExecutor.class);
        return executor;
    }

    public static IOperatingSystem operatingSystem() {
        IProcessExecutor executor = processExecutor();
        IProcessKiller killer = processKiller();
        IOperatingSystem system = mock(IOperatingSystem.class);
        when(system.getProcessExecutor()).thenReturn(executor);
        when(system.getProcessKiller()).thenReturn(killer);
        return system;
    }

    public static EnvironmentData environmentData() throws ProgramSettingsNotFoundException {
        IOperatingSystem system = operatingSystem();
        EnvironmentData environmentData = mock(EnvironmentData.class);
        when(environmentData.getClientSettings()).thenReturn(
                com.autoupdater.client.environment.settings.Mocks.clientSettings());
        when(environmentData.getProgramsSettings()).thenReturn(
                com.autoupdater.client.environment.settings.Mocks.programsSettings());
        when(environmentData.getSystem()).thenReturn(system);
        when(environmentData.findProgramSettingsForUpdate(Matchers.<Update> any())).thenReturn(
                com.autoupdater.client.environment.settings.Mocks.programSettings());
        when(environmentData.getAvailabilityFilter()).thenReturn(
                new AvailabilityFilter(new TreeSet<Program>(), new TreeSet<Program>()));
        return environmentData;
    }
}

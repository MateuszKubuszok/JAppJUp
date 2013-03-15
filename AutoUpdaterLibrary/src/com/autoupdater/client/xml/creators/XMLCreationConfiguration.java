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
package com.autoupdater.client.xml.creators;

import java.nio.charset.Charset;

/**
 * Contains hardcoded configuration used by XMLCreators.
 * 
 * @see com.autoupdater.client.xml.creators.ConfigurationXMLCreator
 * @see com.autoupdater.client.xml.creators.InstallationDataXMLCreator
 */
public final class XMLCreationConfiguration {
    /**
     * Contents of a heading comment in each generated file. Warns of manual
     * edition of generated files.
     */
    public static final String DO_NOT_EDIT_FILE_MANUALLY_WARNING = "This XML configuration file was automaticaly created "
            + "by AutoUpdater library, and can be generater anew at any time! "
            + "Please, use library methods to change configuration.";

    /**
     * Name of encoding used in generated documents.
     */
    public static final String XML_ENCODING_NAME = "UTF-8";

    /**
     * Charset used in generated documents.
     */
    public static final Charset XML_ENCODING = Charset.availableCharsets().get(XML_ENCODING_NAME);
}

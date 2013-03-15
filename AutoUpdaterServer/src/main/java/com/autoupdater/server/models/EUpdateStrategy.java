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
package com.autoupdater.server.models;

/**
 * Model of update strategy.
 */
public enum EUpdateStrategy {
    /**
     * Strategy used by Client to unzip update into installation directory.
     */
    UNZIP("unzip"),

    /**
     * Strategy used by Client to copy update into installation directory.
     */
    COPY("copy"),

    /**
     * Strategy used by Client to run update file as executable with parameters
     * from Update's updaterCommand field.
     */
    EXECUTE("execute");

    private String message;

    private EUpdateStrategy(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

    /**
     * Returns EUpdateStrategy with given message.
     * 
     * @param message
     *            searched message
     * @return EUpdateStrategy if found, null otherwise
     */
    public static EUpdateStrategy parse(String message) {
        for (EUpdateStrategy strategy : EUpdateStrategy.values())
            if (strategy.getMessage().equals(message))
                return strategy;
        return null;
    }
}

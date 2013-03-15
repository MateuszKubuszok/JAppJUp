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
 * Model of types of User's privileges.
 */
public enum EUserType {
    UNPRIVILEDGED("no priviledges", false, false), //
    PACKAGE_ADMIN("package administration", false, true), //
    REPO_ADMIN("repository administration", true, false), //
    SUPERADMIN("access to all controls", true, true);

    private final String message;
    private final boolean admin;
    private final boolean packageAdmin;

    private EUserType(String message, boolean admin, boolean packageAdmin) {
        this.message = message;
        this.admin = admin;
        this.packageAdmin = packageAdmin;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isPackageAdmin() {
        return packageAdmin;
    }

    @Override
    public String toString() {
        return message;
    }

    /**
     * Returns EUserType with given message.
     * 
     * @param message
     *            searched message
     * @return EUserType if found, null otherwise
     */
    public static EUserType parse(String message) {
        for (EUserType type : EUserType.values())
            if (type.getMessage().equals(message))
                return type;
        return null;
    }

    public static EUserType parse(boolean admin, boolean packageAdmin) {
        return admin ? (packageAdmin ? SUPERADMIN : REPO_ADMIN) : (packageAdmin ? PACKAGE_ADMIN
                : UNPRIVILEDGED);
    }
}

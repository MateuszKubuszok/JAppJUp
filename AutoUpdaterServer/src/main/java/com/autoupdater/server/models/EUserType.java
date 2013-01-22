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

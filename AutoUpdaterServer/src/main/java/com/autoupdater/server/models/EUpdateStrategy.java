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

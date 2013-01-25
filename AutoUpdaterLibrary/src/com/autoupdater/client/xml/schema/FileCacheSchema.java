package com.autoupdater.client.xml.schema;

public class FileCacheSchema {
    public static final String files = "files";

    public static final class Files {

        public static final String file = "file";
        public static final String file_ = files + "/" + file;

        public static class File {
            public static final String path = "path";
            public static final String path_ = file_ + path;

            public static final String hash = "hash";
            public static final String hash_ = file_ + hash;
        }
    }
}

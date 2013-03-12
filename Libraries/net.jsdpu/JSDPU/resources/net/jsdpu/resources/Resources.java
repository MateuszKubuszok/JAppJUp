package net.jsdpu.resources;

import static com.google.common.io.ByteStreams.copy;
import static java.io.File.separator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.Files;

/**
 * Class managing resources used by jar, e.g. UACHandler.exe.
 */
public class Resources {
    private static final String uacHandler = "UACHandler.exe";
    private static final String uacPerformer = "UACPerformer.exe";

    private static String tmpDir = null;

    private Resources() {
    }

    /**
     * Installs UACHandler.exe and UACPerformer.exe allowing privilege elevation
     * on Windows.
     */
    public static void installWindowsWrappers() {
        if (tmpDir == null) {
            File tmpDirFile = Files.createTempDir();

            try {
                File handler = new File(tmpDirFile.getAbsolutePath() + separator + uacHandler);
                create(handler, Resources.class.getResourceAsStream(uacHandler));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            try {
                File performer = new File(tmpDirFile.getAbsolutePath() + separator + uacPerformer);
                create(performer, Resources.class.getResourceAsStream(uacPerformer));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            tmpDir = tmpDirFile.getAbsolutePath();
        }
    }

    /**
     * Removes UACHandler.exe and UACPerformer.exe from system.
     */
    public static void uninstallWindowsWrapper() {
        if (tmpDir != null) {
            File uacHandlerFile = new File(tmpDir + separator + uacHandler);
            if (uacHandlerFile.exists())
                uacHandlerFile.delete();
            File uacPerformerFile = new File(tmpDir + separator + uacPerformer);
            if (uacPerformerFile.exists())
                uacPerformerFile.delete();
        }
    }

    /**
     * Returns path to UACHandler.exe file.
     * 
     * @return path to UACHandler.exe
     */
    public static String getUACHandlerPath() {
        installWindowsWrappers();
        return tmpDir + separator + uacHandler;
    }

    /**
     * Creates file from InputStream at given location.
     * 
     * @param file
     *            file to create
     * @param is
     *            InputStream
     * @throws IOException
     *             thrown if file cannot be created
     */
    private static void create(File file, InputStream is) throws IOException {
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        copy(is, fos);
        is.close();
        fos.close();
    }
}

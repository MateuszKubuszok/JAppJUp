package com.autoupdater.server.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Implementation of FileService.
 * 
 * @see com.autoupdater.server.services.FileService
 */
@Service
public class FileServiceImp implements FileService {
	/**
     * Service's logger.
     */
    private static Logger logger = Logger.getLogger(FileServiceImp.class);
	
	private final static Map<String,String> prefixes;
	static {
		prefixes = new HashMap<String,String>();
		prefixes.put("home:", System.getProperty("user.home") + File.separator);
		prefixes.put("run:", System.getProperty("user.dir") + File.separator);
	}
	
    private String storageDirectory;

    private final Random random = new Random();

    @Override
    public void saveFile(String storagePath, byte[] content) throws IOException {
    	logger.debug("Attempting to save File: " + storagePath);
        Path path = Paths.get(storagePath);
        Files.createDirectories(path.getParent());
        Files.createFile(path);
        FileOutputStream fos;
        fos = new FileOutputStream(new File(storagePath));
        fos.write(content);
        fos.close();
        logger.debug("Saved File: " + storagePath);
    }

    @Override
    public synchronized String saveMultipartFile(CommonsMultipartFile multipartFile)
            throws IOException {
    	logger.debug("Attempting to save MultipartFile: " + multipartFile.getName());
        String storagePath = createStoragePath();
        saveFile(getStorageDirectory() + storagePath, multipartFile.getBytes());
        logger.debug("Saved MultipartFile: " + multipartFile.getName());
        return storagePath;
    }

    @Override
    public synchronized InputStream loadFile(String storagePath) throws FileNotFoundException {
    	logger.debug("Attempting to load File: " + storagePath);
        FileInputStream fis = new FileInputStream(new File(getStorageDirectory() + storagePath));
        logger.debug("Loaded File: " + storagePath);
        return fis;
    }
    
    @Override
    public void removeFile(String storagePath) {
    	logger.debug("Attempting to remove File: " + storagePath);
    	try {
			Files.delete(Paths.get(getStorageDirectory() + storagePath));
		} catch (IOException e) {
			logger.error("Couldn't locate file: " + storagePath);
			return;
		}
    	logger.debug("File removed: " + storagePath);
    }
    
    @Value("#{repoProperties['storage.mapping']}")
    public void setStorageDirectory(String rawStorageDirectory) {
    	logger.debug("Initializing storageDirectory");
    	rawStorageDirectory += File.separator;
    	
    	for (Entry<String,String> entry : prefixes.entrySet()) {
    		String prefix = entry.getKey();
    		String path = entry.getValue();
    		if (rawStorageDirectory.startsWith(prefix)) {
    			rawStorageDirectory = path + rawStorageDirectory.substring(prefix.length());
    			break;
    		}
    	}
    	
    	storageDirectory = rawStorageDirectory;
    	logger.debug("storageDirectory initialized with: " + storageDirectory);
    }

    private String getStorageDirectory() {
        return storageDirectory;
    }

    private String createStoragePath() {
    	logger.debug("Attempting to create storage path for file");
        String destinationPath;

        do {
            destinationPath = String.valueOf(random.nextLong());
        } while (Files.exists(Paths.get(getStorageDirectory() + destinationPath)));
        
        logger.debug("Storage path for file created");
        return destinationPath;
    }
}

package net.jsdpu.logger;

import static com.google.common.base.Joiner.on;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LogManager {
	private final java.util.logging.LogManager logManager;
	
	public static LogManager getLogManager() {
		return new LogManager(java.util.logging.LogManager.getLogManager());
	}
	
	private LogManager(java.util.logging.LogManager logManager) {
		this.logManager = logManager;
	}
	
	public void readConfiguration(InputStream is) throws IOException {
		Properties properties = new Properties();
		properties.load(is);
		for (Object key : properties.keySet()) {
			Level level = Level.parse(properties.getProperty((String) key));
			if (level != null)
				properties.setProperty((String) key, level.getOrignialLevel().getName());
		}
		logManager.readConfiguration(new ByteArrayInputStream(on('\n').join(properties.entrySet()).getBytes()));
	} 
}

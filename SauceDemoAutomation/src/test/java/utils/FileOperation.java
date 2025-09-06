package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read configuration from config.properties file.
 * Industry standard way to centralize environment/test settings.
 */

public class FileOperation {
	private static Properties properties;

    // Static block executes once when class is loaded
    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load config.properties file.", e);
        }
    }

    /**
     * Get property value by key.
     * @param key property key from config.properties
     * @return value of property
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}



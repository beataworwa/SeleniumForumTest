package task.selenium.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final String PROPERTIES_FILE_PATH = "/properties/test.properties";

    public Properties loadPropertiesFromFile() {
        Properties prop = new Properties();
        InputStream inputStream = this.getClass().getResourceAsStream(PROPERTIES_FILE_PATH);
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}

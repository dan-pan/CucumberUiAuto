package support;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * Utility class define a few common methods.
 */
public class Utility {

    /**
     * Fetch a yaml file content.
     * @param ymlFilePath yml file path
     * @return a map or null if file is not found
     */
    public static Map<String, Object> fetchYmlFile(String ymlFilePath) {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(ymlFilePath);
        } catch (FileNotFoundException ex) {
            System.out.println("File (" + ymlFilePath + ") is not found");
            return null;
        }
        return yaml.load(inputStream);
    }
}

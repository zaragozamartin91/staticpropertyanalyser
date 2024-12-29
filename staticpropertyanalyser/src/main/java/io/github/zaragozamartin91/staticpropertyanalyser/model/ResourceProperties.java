package io.github.zaragozamartin91.staticpropertyanalyser.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.Properties;

public class ResourceProperties {
    Properties properties;

    public static ResourceProperties fromFile(File file) {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read properties file " + file, e);
        }

        return new ResourceProperties(properties);
    }

    public ResourceProperties(Properties properties) {
        this.properties = properties;
    }

    public void listProperties() {
        properties.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    public ResourceProperties sort() {
        // this is meaningless since Properties is a Map and it is not ordered
        Properties sortedProperties = new Properties();
        properties.entrySet()
                  .stream()
                  .sorted(Comparator.comparing(e -> e.getKey().toString()))
                  .forEach(e -> sortedProperties.put(e.getKey(), e.getValue()));
        return new ResourceProperties(sortedProperties);
    }

    public void store(OutputStream out, String comments) {
        try {
            // Storing properties also sorts them...
            properties.store(out, comments);
        } catch (IOException e) {
            throw new IllegalStateException("Error while storing properties to file", e);
        }
    }
}

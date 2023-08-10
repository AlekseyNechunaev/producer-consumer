package ru.nechunaev.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static Properties readPropertiesFromFile(String filePath) {
        try (InputStream reader = new FileInputStream(filePath)) {
            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            log.error("error when working with the file");
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Integer> convert(Properties properties) {
        Map<String, Integer> convertProperties = new HashMap<>(properties.size());
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            final String key = String.valueOf(entry.getKey());
            final Integer value = convertToNumber(entry.getValue());
            convertProperties.put(key, value);
        }
        return convertProperties;
    }

    private static Integer convertToNumber(Object value) {
        try {
            return Integer.valueOf(value.toString());
        } catch (NumberFormatException e) {
            log.error("error when converting an argument {} to a number", value, e);
            throw e;
        }
    }

    private Utils() {

    }
}

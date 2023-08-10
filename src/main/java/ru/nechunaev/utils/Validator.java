package ru.nechunaev.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nechunaev.constant.PropertyNames;
import ru.nechunaev.exception.ValidationException;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class Validator {

    private static final Logger log = LoggerFactory.getLogger(Validator.class);

    public static void checkValidProperties(Properties properties) {
        final Set<String> validNames = Set.of(
                PropertyNames.STORAGE_SIZE,
                PropertyNames.CONSUMER_COUNT,
                PropertyNames.PRODUCER_COUNT
        );
        if (properties.isEmpty()) {
            log.error("properties cannot be empty");
            throw new ValidationException();
        }
        Set<Object> propertiesKeys = properties.keySet();
        final int actualPropertiesSize = propertiesKeys.size();
        final int exceptedPropertiesSize = validNames.size();
        if (actualPropertiesSize != exceptedPropertiesSize) {
            log.error("invalid number of arguments = {}, excepted = {}", actualPropertiesSize, exceptedPropertiesSize);
            throw new ValidationException();
        }
        Set<String> notValidKeys = propertiesKeys.stream()
                .filter(key -> !validNames.contains(key))
                .map(String::valueOf)
                .collect(Collectors.toSet());
        if (!notValidKeys.isEmpty()) {
            log.error("incorrect argument names detected = {}", notValidKeys);
            throw new ValidationException();
        }
    }

    public static void checkArguments(Map<String, Integer> properties) {
        Map<String, Integer> notValidArgs = properties.entrySet().stream()
                .filter(entry -> entry.getValue() == null || entry.getValue() <= 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (!notValidArgs.isEmpty()) {
            log.error("invalid arguments found {}", notValidArgs);
            throw new ValidationException();
        }
    }
}

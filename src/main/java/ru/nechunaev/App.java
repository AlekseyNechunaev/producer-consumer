package ru.nechunaev;

import ru.nechunaev.constant.PropertyNames;
import ru.nechunaev.utils.Utils;
import ru.nechunaev.utils.Validator;

import java.util.Map;
import java.util.Properties;

public class App {

    public static void main(String[] args) {
        final String confFilePath = "configuration.properties";
        Properties properties = Utils.readPropertiesFromFile(confFilePath);
        Validator.checkValidProperties(properties);
        Map<String, Integer> convertProperties = Utils.convert(properties);
        Validator.checkArguments(convertProperties);
        final CustomQueue queue = new CustomQueue(convertProperties.get(PropertyNames.STORAGE_SIZE));
        final int consumerCount = convertProperties.get(PropertyNames.CONSUMER_COUNT);
        final int producerCount = convertProperties.get(PropertyNames.PRODUCER_COUNT);
        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);
        for (int i = 0; i < consumerCount; i++) {
            Thread thread = new Thread(consumer);
            thread.start();
        }
        for (int i = 0; i < producerCount; i++) {
            Thread thread = new Thread(producer);
            thread.start();
        }
    }
}

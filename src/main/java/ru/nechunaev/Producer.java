package ru.nechunaev;

import java.util.UUID;

public class Producer implements Runnable {

    private final CustomQueue customQueue;

    public Producer(CustomQueue customQueue) {
        this.customQueue = customQueue;
    }

    @Override
    public void run() {
        Resource resource = new Resource(UUID.randomUUID());
        customQueue.produce(resource);
    }
}

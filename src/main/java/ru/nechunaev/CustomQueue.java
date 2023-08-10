package ru.nechunaev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

public class CustomQueue {
    private static final Logger log = LoggerFactory.getLogger(CustomQueue.class);
    private final Queue<Resource> resources = new LinkedList<>();
    private final Object lock = new Object();
    private final int maxSize;
    private long lastProduce;

    public CustomQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void produce(Resource resource) {
        synchronized (lock) {
            final Long currentThreadId = Thread.currentThread().getId();
            while (resources.size() == maxSize) {
                try {
                    log.info("Thread PRODUCE with id {} is waiting", currentThreadId);
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            resources.add(resource);
            log.info("Thread with id {} produce resource with resourceId = {}", currentThreadId, resource.getId());
            log.info("number of resources in stock = {} after produce", resources.size());
            lastProduce = System.currentTimeMillis();
            lock.notifyAll();
        }
    }

    public void consume() {
        synchronized (lock) {
            final Long currentThreadId = Thread.currentThread().getId();
            while (resources.isEmpty()) {
                try {
                    log.info("Thread CONSUME with id {} is waiting", currentThreadId);
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Resource resource = resources.remove();
            log.info("Thread with id {} consume resource with resourceId = {}", currentThreadId, resource.getId());
            log.info("number of resources in stock = {} after consume", resources.size());
            lock.notifyAll();
        }
    }
}

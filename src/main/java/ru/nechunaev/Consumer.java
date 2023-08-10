package ru.nechunaev;

public class Consumer implements Runnable {

    private final CustomQueue queue;

    public Consumer(CustomQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        queue.consume();
    }
}

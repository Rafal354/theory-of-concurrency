package tw.lab4.zad2b;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {
    private final int id;
    private final Buffer buffer;

    public Consumer(Buffer buffer, int id) {
        this.id = id;
        this.buffer = buffer;
    }

    public void run() {
        System.out.println("Thread starting, id: " + id);
        while (true) {
            try {
                int number = ThreadLocalRandom.current().nextInt(1, buffer.getSize() / 2);
                buffer.get(number, id);
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

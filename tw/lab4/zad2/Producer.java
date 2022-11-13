package tw.lab4.zad2;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private final int id;
    private final Buffer buffer;

    public Producer(Buffer buffer, int id) {
        this.id = id;
        this.buffer = buffer;
    }

    public void run() {
        System.out.println("Thread starting, id: " + id);
        while (true) {
            try {
                int number = ThreadLocalRandom.current().nextInt(1, buffer.getSize() / 2);
                buffer.put(number, id);
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

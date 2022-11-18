package tw.lab4.zad2;

import java.util.Random;
import static java.lang.Math.abs;

public class Consumer implements Runnable {
    private final int id;
    private final Buffer buffer;

    public Consumer(Buffer buffer, int id) {
        this.id = id;
        this.buffer = buffer;
    }

    public void run() {
        Random random = new Random();
        for (int i = 0; i < buffer.getRounds(); i++) {
            try {
                int number = abs(random.nextInt()) % ((buffer.getSize() + 1) / 2) - 1;
                buffer.get(number, id);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

package tw.lab4.zad1;

import java.util.concurrent.ThreadLocalRandom;

public class Person implements Runnable {
    private final int id;
    private final AbstractBuffer buffer;

    public Person(AbstractBuffer buffer, int id) {
        this.id = id;
        this.buffer = buffer;
    }

    public void run() {
        int timeToWait = ThreadLocalRandom.current().nextInt(0, 100);
        System.out.println("ID: " + id + ", TIME: " + timeToWait);
        try {
            Thread.sleep(100);
            for (int i = 0; i < buffer.getNumberOfRounds(); i++) {
                for (int j = 0; j < buffer.getSizeOfBuffer(); j++) {
                    buffer.doTheJob(id, j);
                    Thread.sleep(timeToWait);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

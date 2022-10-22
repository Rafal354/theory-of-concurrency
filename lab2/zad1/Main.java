package lab2.zad1;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int value = 10000000;
        Counter counter = new Counter();
        racing(counter, value);
    }
    private static void racing (Counter counter, int value) throws InterruptedException {
        out.println("Begin with: " + counter.getCounter());
        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < value; i++) {
                try {
                    counter.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < value; i++) {
                try {
                    counter.decrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
        out.println("End with: " + counter.getCounter());
    }
}

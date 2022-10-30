package tw.lab1.zad1;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int value = 100000000;
        Counter counter = new Counter();
        out.println("NOT SYNCHRONIZED");
        racing(counter, value);

        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();
        out.println("SYNCHRONIZED");
        racing(synchronizedCounter, value);
    }
    private static void racing(Counter counter, int value) throws InterruptedException {
        out.println("Begin with: " + counter.getCounter());
        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < value; i++) {
                counter.increment();
            }
        });
        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < value; i++) {
                counter.decrement();
            }
        });
        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
        out.println("End with: " + counter.getCounter());
    }
}

package tw.lab2.zad2;

import java.util.Random;

public class Customer implements Runnable {
    int id;
    Semaphore semaphore;
    int numberOfIterations;

    public Customer(Semaphore semaphore, int numberOfIterations, int id) {
        this.id = id;
        this.semaphore = semaphore;
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfIterations; i++) {
            Random random = new Random();
            int x = random.nextInt(9);
            System.out.println("Customer: " + id + " random is: " + x);
            try {
                this.semaphore.down();
                System.out.println("Customer: " + id + " is shopping!");
                Thread.sleep(x * 1000);
                this.semaphore.up();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

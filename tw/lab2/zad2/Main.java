package tw.lab2.zad2;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int numberOfIterations = 3;
        int numberOfCustomers = 5;
        int sizeOfShop = 3;
        shopping(numberOfIterations, numberOfCustomers, sizeOfShop);
    }
    private static void shopping(int numberOfIterations, int numberOfCustomers, int size) throws InterruptedException {
        Semaphore semaphore = new Semaphore(size);
        ArrayList<Thread> customers = new ArrayList<>();

        for (int i = 0; i < numberOfCustomers; i++) {
            customers.add(new Thread(new Customer(semaphore, numberOfIterations, i)));
        }

        for (Thread thread: customers) {
            thread.start();
        }

        for (Thread thread: customers) {
            thread.join();
        }
    }
}

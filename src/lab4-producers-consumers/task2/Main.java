package tw.lab4.zad2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int sizeOfBuffer = 100;
        int numberOfEmp = 2000;
        int rounds = 25;

//        Buffer buffer = new NaiveBuffer(sizeOfBuffer, rounds);
        Buffer buffer = new FairBuffer(sizeOfBuffer, rounds);

        ArrayList<Thread> workers = new ArrayList<>();
        for (int i = 0; i < numberOfEmp; i += 2) {
            workers.add(new Thread(new Producer(buffer, i)));
        }
        for (int i = 1; i < numberOfEmp; i += 2) {
            workers.add(new Thread(new Consumer(buffer, i)));
        }
        for (Thread thread: workers) {
            thread.start();
        }
        for (Thread thread: workers) {
            thread.join();
        }
    }
}

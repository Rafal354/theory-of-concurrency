package tw.lab4.zad2b;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int sizeOfBuffer = 10;
        int numberOfEmp = 10;
        Buffer buffer = new Buffer(sizeOfBuffer);

        ArrayList<Thread> workers = new ArrayList<>();

        for (int i = 0; i < numberOfEmp / 2; i++) {
            workers.add(new Thread(new Producer(buffer, i)));
        }
        for (int i = numberOfEmp / 2; i < numberOfEmp; i++) {
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

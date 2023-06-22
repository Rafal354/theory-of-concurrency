package tw.lab4.zad1;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int numberOfWorkers = 20;
        int numberOfRounds = 5;
        int sizeOfBuffer = 100;
        BufferSignalOneThread buffer = new BufferSignalOneThread(sizeOfBuffer, numberOfRounds, numberOfWorkers);
//        Buffer buffer = new Buffer(sizeOfBuffer, numberOfRounds, numberOfWorkers);
//        Buffer buffer = new BufferSyncOutput(sizeOfBuffer, numberOfRounds, numberOfWorkers);

        ArrayList<Thread> threads = new ArrayList<>();

        threads.add(new Thread(new Producer(buffer)));
        for (int i = 1; i <= numberOfWorkers; i++) {
            threads.add(new Thread(new Worker(buffer, i)));
        }
        threads.add(new Thread(new Consumer(buffer)));

        for (Thread thread: threads) {
            thread.start();
        }
        for (Thread thread: threads) {
            thread.join();
        }
    }
}

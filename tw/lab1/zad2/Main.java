package tw.lab1.zad2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Buffer buffer = new Buffer();
        int numberOfConsumers = 9;
        int numberOfProducers = 9;
        Amount.setAmount(10000);

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0 ; i < numberOfProducers; i++) {
            Thread newProducer = new Thread(new Producer(buffer));
            newProducer.start();
            threads.add(newProducer);
        }

        for (int i = 0; i <numberOfConsumers; i++) {
            Thread newConsumer = new Thread(new Consumer(buffer));
            newConsumer.start();
            threads.add(newConsumer);
        }
        for (Thread thread: threads) {
            thread.join();
        }
    }
}

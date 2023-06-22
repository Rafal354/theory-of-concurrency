package tw.lab3.zad3;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int rounds = 3;
        int numberOfPairs = 4;
        Waiter waiter = new Waiter(numberOfPairs);
        ArrayList<Thread> clients = new ArrayList<>();

        for (int i = 0; i < numberOfPairs * 2; i++) {
            clients.add(new Thread(new Client(i, rounds, waiter)));
        }
        for (Thread thread: clients) {
            thread.start();
        }
        for (Thread thread: clients) {
            thread.join();
        }
    }
}

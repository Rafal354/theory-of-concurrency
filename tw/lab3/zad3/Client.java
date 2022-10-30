package tw.lab3.zad3;

import tw.ConsoleColors;

public class Client implements Runnable {
    private final int id;
    private final int rounds;
    private final Waiter waiter;

    public Client(int id, int rounds, Waiter waiter) {
        this.id = id;
        this.rounds = rounds;
        this.waiter = waiter;
    }
    public void run() {
        System.out.println(ConsoleColors.RED + "I am running customer nr: " + id);
        for(int i = 0; i < rounds; i++) {
            try {
                privateMatters();
                waiter.wantTable(id);
                eating();
                waiter.leave();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void eating() throws InterruptedException {
        Thread.sleep(2000);
    }
    private void privateMatters() throws InterruptedException {
        Thread.sleep(5000);
    }
}

package tw.lab4.zad2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveBuffer extends Buffer {
    private final Lock lock = new ReentrantLock();
    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();
    private final int rounds;
    private final int size;
    private int state = 0;

    public NaiveBuffer(int size, int rounds) {
        this.rounds = rounds;
        this.size = size;
    }
    public void put(int number, int id) throws InterruptedException {

        long startTime = System.nanoTime();
        lock.lock();

        while(number + state > size) {
//            System.out.println(ConsoleColors.BLUE + "Waiting to put: " + number + ", ID: " + id);
            full.await();
        }
        state += number;
//        System.out.println(ConsoleColors.YELLOW + "Put: " + number + ", ID: " + id);
//        System.out.println(ConsoleColors.GREEN + "STATE: " + state);

        empty.signalAll();
        lock.unlock();
        long endTime = System.nanoTime();
        System.out.println("P " + (endTime - startTime));
    }
    public void get(int number, int id) throws InterruptedException {

        long startTime = System.nanoTime();
        lock.lock();

        while(state - number < 0) {
//            System.out.println(ConsoleColors.BLUE_BRIGHT + "Waiting to get: " + number + ", ID: " + id);
            empty.await();
        }
        state -= number;
//        System.out.println(ConsoleColors.RED + "Get: " + number + ", ID: " + id);
//        System.out.println(ConsoleColors.GREEN + "STATE: " + state);

        full.signalAll();
        lock.unlock();
        long endTime = System.nanoTime();
        System.out.println("C " + (endTime - startTime));
    }
    public int getSize() {
        return this.size;
    }
    public int getRounds() {
        return this.rounds;
    }
}

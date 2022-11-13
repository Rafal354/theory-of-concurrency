package tw.lab4.zad2;

import tw.ConsoleColors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final Lock lock = new ReentrantLock();
    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();
    private final int size;
    private int state;

    public Buffer(int size) {
        this.size = size;
        this.state = 0;
    }
    public void put(int number, int id) throws InterruptedException {
        lock.lock();
        while(number + state > size) {
            System.out.println(ConsoleColors.BLUE + "Waiting to put: " + number + ", ID: " + id);
            full.await();
        }
        state += number;
        System.out.println(ConsoleColors.YELLOW + "Put: " + number + ", ID: " + id);
        System.out.println(ConsoleColors.GREEN + "STATE: " + state);
        empty.signalAll();
        lock.unlock();
    }
    public void get(int number, int id) throws InterruptedException {
        lock.lock();
        while(state - number < 0) {
            System.out.println(ConsoleColors.BLUE_BRIGHT + "Waiting to get: " + number + ", ID: " + id);
            empty.await();
        }
        state -= number;
        System.out.println(ConsoleColors.RED + "Get: " + number + ", ID: " + id);
        System.out.println(ConsoleColors.GREEN + "STATE: " + state);
        full.signalAll();
        lock.unlock();
    }
    public int getSize() {
        return size;
    }
}

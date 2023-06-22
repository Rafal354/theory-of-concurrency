package tw.lab4.zad2;

import tw.ConsoleColors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairBuffer extends Buffer {
    private final Lock lock = new ReentrantLock();
    private final Condition firstProd = lock.newCondition();
    private final Condition firstCons = lock.newCondition();
    private final Condition restProd = lock.newCondition();
    private final Condition restCons = lock.newCondition();
    private boolean prodFlag = false;
    private boolean consFlag = false;
    private final int rounds;
    private final int size;
    private int state = 0;

    public FairBuffer(int size, int rounds) {
        this.rounds = rounds;
        this.size = size;
    }
    public void put(int number, int id) throws InterruptedException {

        long startTime = System.nanoTime();
        lock.lock();

        if (prodFlag) {
            restProd.await();
        }
        prodFlag = true;

        while(number + state > size) {
//            System.out.println(ConsoleColors.BLUE + "Waiting to put: " + number + ", ID: " + id);
            firstProd.await();
        }
        state += number;
//        System.out.println(ConsoleColors.YELLOW + "Put: " + number + ", ID: " + id);
//        System.out.println(ConsoleColors.GREEN + "STATE: " + state);

        prodFlag = false;
        restProd.signal();
        firstCons.signal();
        lock.unlock();
        long endTime = System.nanoTime();
        System.out.println("P " + (endTime - startTime));
    }
    public void get(int number, int id) throws InterruptedException {

        long startTime = System.nanoTime();
        lock.lock();

        if (consFlag) {
            restCons.await();
        }
        consFlag = true;

        while(state - number < 0) {
//            System.out.println(ConsoleColors.BLUE_BRIGHT + "Waiting to get: " + number + ", ID: " + id);
            firstCons.await();
        }
        state -= number;
//        System.out.println(ConsoleColors.RED + "Get: " + number + ", ID: " + id);
//        System.out.println(ConsoleColors.GREEN + "STATE: " + state);

        consFlag = false;
        restCons.signal();
        firstProd.signal();
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

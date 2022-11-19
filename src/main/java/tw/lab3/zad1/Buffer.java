package tw.lab3.zad1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();
    private String message;
    private boolean occupied;
    public Buffer() {
        this.occupied = false;
    }
    public String take() throws InterruptedException {
        lock.lock();
        try {
            while (!occupied) {
                notEmpty.await();
            }
            occupied = false;
            System.out.println("Consumer: " + message);
            notFull.signal();
            return message;
        } finally {
            lock.unlock();
        }

    }
    public void put(String message) throws InterruptedException {
        lock.lock();
        try {
            while (occupied) {
                notFull.await();
            }
            occupied = true;
            this.message = message;
            System.out.println("Producer: " + message);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}

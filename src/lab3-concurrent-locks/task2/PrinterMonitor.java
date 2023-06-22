package tw.lab3.zad2;

import java.util.*;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {
    private final Lock lock = new ReentrantLock();
    private final Condition empty = lock.newCondition();
    private Map<Integer, Printer> printersInUse = new HashMap<>();

    private Queue<Printer> printers = new LinkedList<>();


    PrinterMonitor(int number) {
        for (int i = 0; i < number; i++) {
            printers.add(new Printer(i));
        }
    }

    public int reserve() throws InterruptedException {
        lock.lock();
        try {
            while (printers.isEmpty()) {
                empty.await();
            }
            Printer printer = printers.remove();
            int id = printer.getId();
            printersInUse.put(printer.getId(), printer);
            return id;
        } finally {
            lock.unlock();
        }
    }

    public void takeBack(int id) {
        lock.lock();
        Printer printer = printersInUse.get(id);
        printers.add(printer);
        empty.signalAll();
        lock.unlock();
    }
}

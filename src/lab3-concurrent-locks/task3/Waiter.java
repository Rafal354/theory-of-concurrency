package tw.lab3.zad3;

import tw.ConsoleColors;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private final Lock lock = new ReentrantLock();
    private Condition[] waitingRoom;
    private final Condition table = lock.newCondition();
    private final int numberOfPairs;
    private int personAtTheTable;
    private int occupiedSeats;
    private Boolean[] insideRestaurant;

    public Waiter(int numberOfPairs) {
        this.numberOfPairs = numberOfPairs;
        this.personAtTheTable = -1;
        this.occupiedSeats = 0;
        this.setConditions();
        this.setFalseInArray();
    }
    public void wantTable(int id) throws InterruptedException {
        lock.lock();
        System.out.println(ConsoleColors.PURPLE + "I am in the restaurant: " + id);
        insideRestaurant[id] = true;
        waitingRoom[getPartnerId(id)].signal();
        while(!insideRestaurant[getPartnerId(id)]) {
            System.out.println(ConsoleColors.BLUE + "I am waiting for my date: " + id);
            waitingRoom[id].await();
        }
        while(occupiedSeats == 2 || (personAtTheTable != getPartnerId(id) && occupiedSeats == 1)) {
            System.out.println(ConsoleColors.CYAN + "I am waiting for the table: " + id);
            table.await();
        }
        occupiedSeats += 1;
        personAtTheTable = id;
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "I am eating: " + id);
        lock.unlock();
    }
    public void leave() {
        lock.lock();
        insideRestaurant[personAtTheTable] = false;
        occupiedSeats -= 1;
        if (occupiedSeats == 0) {
            table.signalAll();
        }
        lock.unlock();
    }
    private int getPartnerId(int id) {
        return id % 2 == 0 ? id + 1 : id - 1;
    }
    private void setFalseInArray() {
        insideRestaurant = new Boolean[numberOfPairs * 2];
        Arrays.fill(insideRestaurant, false);
    }
    private void setConditions() {
        this.waitingRoom = new Condition[numberOfPairs * 2];
        for (int i = 0; i < numberOfPairs * 2; i++) {
            waitingRoom[i] = lock.newCondition();
        }
    }
}

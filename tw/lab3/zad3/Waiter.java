package tw.lab3.zad3;

import tw.ConsoleColors;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private final Lock lock = new ReentrantLock();
    private final Condition waitingRoom = lock.newCondition();
    private final Condition table = lock.newCondition();

    private final int numberOfPairs;
    private int personAtTheTable;
    private int occupiedSets;
    private Boolean[] insideRestaurant;

    public Waiter(int numberOfPairs) {
        this.numberOfPairs = numberOfPairs;
        this.personAtTheTable = -1;
        this.occupiedSets = 0;
        this.setFalseInArray();
    }
    public void wantTable(int id) throws InterruptedException {
        lock.lock();
        System.out.println(ConsoleColors.PURPLE + "I am in the restaurant: " + id);
        insideRestaurant[id] = true;
        waitingRoom.signalAll();
        while(!insideRestaurant[getPartnerId(id)]) {
            System.out.println(ConsoleColors.BLUE + "I am waiting for my date: " + id);
            waitingRoom.await();
        }
        while(occupiedSets == 2 || (personAtTheTable != getPartnerId(id) && occupiedSets == 1)) {
            System.out.println(ConsoleColors.CYAN + "I am waiting for the table: " + id);
            table.await();
        }
        occupiedSets += 1;
        personAtTheTable = id;
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "I am eating: " + id);
        lock.unlock();
    }
    public void leave() {
        lock.lock();
        insideRestaurant[personAtTheTable] = false;
        insideRestaurant[getPartnerId(personAtTheTable)] = false;
        occupiedSets -= 1;
        table.signalAll();
        lock.unlock();
    }
    private int getPartnerId(int id) {
        return id % 2 == 0 ? id + 1 : id - 1;
    }
    private void setFalseInArray() {
        insideRestaurant = new Boolean[numberOfPairs * 2];
        Arrays.fill(insideRestaurant, false);
    }
}

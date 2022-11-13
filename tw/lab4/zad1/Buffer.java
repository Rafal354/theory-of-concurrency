package tw.lab4.zad1;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer extends AbstractBuffer {
    private ReentrantLock[] locks;
    private Condition[] bufferConditions;
    private Integer[] line;
    private final int sizeOfBuffer;
    private final int numberOfRounds;
    private final int numberOfWorkers;

    public Buffer(int sizeOfBuffer, int numberOfRounds, int numberOfWorkers) {
        this.sizeOfBuffer = sizeOfBuffer;
        this.numberOfRounds = numberOfRounds;
        this.numberOfWorkers = numberOfWorkers;
        setLocks();
        setLine();
    }
    public void doTheJob(int id, int index) {
        locks[index].lock();
        while (line[index] != id - 1) {
            try {
                bufferConditions[index].await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        line[index] = insertNewValue(id);
        System.out.println(Arrays.toString(line));
        bufferConditions[index].signalAll();
        locks[index].unlock();
    }
    public int getSizeOfBuffer() {
        return sizeOfBuffer;
    }
    public int getNumberOfRounds() {
        return numberOfRounds;
    }
    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }
    private void setLine() {
        line = new Integer[sizeOfBuffer];
        Arrays.fill(line, -1);
    }
    private void setLocks() {
        locks = new ReentrantLock[sizeOfBuffer];
        bufferConditions = new Condition[sizeOfBuffer];
        for (int i = 0; i < sizeOfBuffer; i++) {
            locks[i] = new ReentrantLock();
            bufferConditions[i] = locks[i].newCondition();
        }
    }
    private int insertNewValue(int id) {
        return id == numberOfWorkers + 1 ? -1 : id;
    }
}

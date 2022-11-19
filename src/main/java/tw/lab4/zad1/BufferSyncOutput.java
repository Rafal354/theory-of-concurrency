package tw.lab4.zad1;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferSyncOutput extends AbstractBuffer {
    private final Lock lock = new ReentrantLock();
    private Condition[] bufferConditions;
    private final int sizeOfBuffer;
    private final int numberOfRounds;
    private final int numberOfWorkers;
    private Integer[] line;

    public BufferSyncOutput(int sizeOfBuffer, int numberOfRounds, int numberOfWorkers) {
        this.sizeOfBuffer = sizeOfBuffer;
        this.numberOfRounds = numberOfRounds;
        this.numberOfWorkers = numberOfWorkers;
        setLine();
        setBufferConditions();
    }
    public void doTheJob(int id, int index) {
        lock.lock();
        while (line[index] != id - 1) {
            try {
                bufferConditions[index].await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        line[index] = insertNewValue(id);
        bufferConditions[index].signalAll();
        System.out.println(Arrays.toString(line));
        lock.unlock();
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
    private void setBufferConditions() {
        bufferConditions = new Condition[sizeOfBuffer];
        for (int i = 0; i < sizeOfBuffer; i++) {
            bufferConditions[i] = lock.newCondition();
        }
    }
    private int insertNewValue(int id) {
        return id == numberOfWorkers + 1 ? -1 : id;
    }
}

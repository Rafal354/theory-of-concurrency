package tw.lab4.zad1;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BufferSignalOneThread extends AbstractBuffer {
    private ReentrantLock[] locks;
    private Condition[][] employeeConditions;
    private Integer[] line;
    private final int sizeOfBuffer;
    private final int numberOfRounds;
    private final int numberOfWorkers;

    public BufferSignalOneThread(int sizeOfBuffer, int numberOfRounds, int numberOfWorkers) {
        this.sizeOfBuffer = sizeOfBuffer;
        this.numberOfRounds = numberOfRounds;
        this.numberOfWorkers = numberOfWorkers;
        this.setLocks();
        this.setLine();
    }
    public void doTheJob(int id, int index) {
        locks[index].lock();
        while (line[index] != id - 1) {
            try {
                employeeConditions[index][prevId(id)].await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        line[index] = insertValue(id);
        System.out.println(Arrays.toString(line));
        employeeConditions[index][id].signal();
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
        employeeConditions = new Condition[sizeOfBuffer][numberOfWorkers + 2];
        for (int i = 0; i < sizeOfBuffer; i++) {
            locks[i] = new ReentrantLock();
            for (int j = 0; j < numberOfWorkers + 2; j++) {
                employeeConditions[i][j] = locks[i].newCondition();
            }
        }
    }
    private int insertValue(int id) {
        return id == numberOfWorkers + 1 ? -1 : id;
    }
    private int prevId(int id) {
        return id == 0 ? numberOfWorkers + 1 : id - 1;
    }
}

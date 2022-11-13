package tw.lab4.zad1;

public abstract class AbstractBuffer {
    public abstract void doTheJob(int id, int index);
    public abstract int getSizeOfBuffer();
    public abstract int getNumberOfRounds();
    public abstract int getNumberOfWorkers();
}

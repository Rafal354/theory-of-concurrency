package tw.lab4.zad2;

public abstract class Buffer {
    public abstract void put(int number, int id) throws InterruptedException;
    public abstract void get(int number, int id) throws InterruptedException;
    public abstract int getSize();
    public abstract int getRounds();
}

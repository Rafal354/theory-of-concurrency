package tw.lab2.zad2;

public class Semaphore {
    private final int size;
    private int value;

    public Semaphore(int size) {
        this.size = size;
        this.value = size;
    }

    synchronized public void up() {
        this.value = Math.min(this.value + 1, size);
        System.out.println("Semaphore: " + this.getValue() + " (up!)");
        notifyAll();
    }

    synchronized public void down() throws InterruptedException {
        while (this.value == 0) {
            System.out.println("Semaphore: " + this.getValue() + " (waiting!)");
            wait();
        }
        this.value -= 1;
        System.out.println("Semaphore: " + this.getValue() + " (down!)");
    }
    private int getValue() {
        return value;
    }
}

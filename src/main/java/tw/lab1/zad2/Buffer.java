package tw.lab1.zad2;

public class Buffer {
    private String message;
    private boolean occupied;
    public Buffer() {
        this.occupied = false;
    }
    synchronized public String take() {
        while (!occupied) {
            try {
                wait();
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
        occupied = false;
        System.out.println("Consumer: " + message);
        notifyAll();
        return message;
    }
    synchronized public void put(String message) {
        while (occupied) {
            try {
                wait();
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
        occupied = true;
        this.message = message;
        System.out.println("Producer: " + message);
        notifyAll();
    }
}

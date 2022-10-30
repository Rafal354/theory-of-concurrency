package tw.lab1.zad2;

public class Consumer implements Runnable {

    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0;  i < Amount.getAmount();   i++) {
            String message = buffer.take();
        }
    }
}

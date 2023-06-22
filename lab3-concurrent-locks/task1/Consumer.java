package tw.lab3.zad1;

public class Consumer implements Runnable {

    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < Amount.getAmount(); i++) {
            try {
                String message = buffer.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

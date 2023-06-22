package tw.lab3.zad1;

public class Producer implements Runnable {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < Amount.getAmount(); i++) {
            try {
                buffer.put("message " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

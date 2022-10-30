package tw.lab1.zad2;

public class Producer implements Runnable {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0;  i < Amount.getAmount(); i++) {
            buffer.put("message " + i);
        }
    }
}

package tw.lab1.zad1;

public class SynchronizedCounter extends Counter {
    @Override
    public void increment() {
        synchronized (this) {
            this.counter += 1;
        }
    }
    @Override
    public void decrement() {
        synchronized (this) {
            this.counter -= 1;
        }
    }
}

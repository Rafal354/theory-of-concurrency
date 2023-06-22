package tw.lab1.zad1;

public class Counter {
    protected Integer counter;

    public Counter() {
        this.counter = 0;
    }

    public void increment() {
            this.counter += 1;
    }

    public void decrement() {
            this.counter -= 1;
    }

    public Integer getCounter() {
        return counter;
    }
}

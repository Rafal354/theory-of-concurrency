package tw.lab2.zad1;

public class Counter {
    protected Integer counter;
    protected SemaphoreBin semaphore;

    public Counter() {
        this.counter = 0;
        this.semaphore = new SemaphoreBin();
    }

    public void increment() throws InterruptedException {
        this.semaphore.down();
        this.counter += 1;
        this.semaphore.up();
    }

    public void decrement() throws InterruptedException {
        this.semaphore.down();
        this.counter -= 1;
        this.semaphore.up();
    }

    public Integer getCounter() {
        return counter;
    }
}

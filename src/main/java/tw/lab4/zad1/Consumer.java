package tw.lab4.zad1;

public class Consumer extends Person {
    public Consumer(AbstractBuffer buffer) {
        super(buffer, buffer.getNumberOfWorkers() + 1);
    }
}

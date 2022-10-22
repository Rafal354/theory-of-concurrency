package lab2.zad1;

public class SemaphoreBin {
    private boolean flag;

    public SemaphoreBin() {
        this.flag = true;
    }

    synchronized public void up() {
        this.flag = true;
        notifyAll();
    }

    synchronized public void down() throws InterruptedException {
        while (!this.flag) {
            wait();
        }
        this.flag = false;
    }
}

package tw.lab3.zad2;

import java.util.Random;

public class PrinterUser implements Runnable {
    int id;
    int sheetsToPrint;
    PrinterMonitor printerMonitor;
    Random random = new Random();

    public PrinterUser(int id, int sheetsToPrint, PrinterMonitor printerMonitor) {
        this.id = id;
        this.sheetsToPrint = sheetsToPrint;
        this.printerMonitor = printerMonitor;
    }

    public void run() {
        System.out.println("Pracownik nr: " + this.id + " startuje!");
        for (int i = 0; i < sheetsToPrint; i++) {
            try {
                int printerId = printerMonitor.reserve();
                int x = random.nextInt(9);
                System.out.println("Pracownik: " + this.id + " drukuje na drukarce nr:  " + printerId + " przez: " + x + " sekund.");
                Thread.sleep(x * 1000);
                printerMonitor.takeBack(printerId);
                Thread.sleep(3 * 1000); // comment
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

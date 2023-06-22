package tw.lab3.zad2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int M = 8;
        int N = 2;
        int sheetToPrint = 10;
        ArrayList<Thread> printerUsers = new ArrayList<>();

        PrinterMonitor printerMonitor = new PrinterMonitor(N);
        for (int i = 0; i < M; i++) {
            printerUsers.add(new Thread(new PrinterUser(i, sheetToPrint, printerMonitor)));
        }

        for (Thread thread: printerUsers) {
            thread.start();
        }

        for (Thread thread: printerUsers) {
            thread.join();
        }

    }
}

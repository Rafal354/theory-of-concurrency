Laboratorium 5 - asynchroniczne wykonanie zadań w puli wątków przy użyciu wzorców Executor i Future

Interfejs java.util.concurrent.Executor i java.util.concurrent.ExecutorService

Executor służy do asynchronicznego wykonania zadań typu Runnable. Zamiast tworzyć osobne wątki:

new Thread(new(RunnableTask())).start()

można użyć metody execute():
 executor.execute(new RunnableTask1());
 executor.execute(new RunnableTask2());
 ...

Metoda submit() w interfejscie ExecutorService działa podobnie do execute(), ale przyjmuje zadania implementujące interfejs Callable, które mogą zwrócić wartość (metoda run() jest typu void). Zadanie może implementować interfejs Future.

Przykład.

Implementacje

Zadania

Proszę zaimplementować przy użyciu Executor i Future program wykonujący obliczanie zbioru Mandelbrota w puli wątków. Jako podstawę implementacji proszę wykorzystać kod w Javie.

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
 
public class Mandelbrot extends JFrame {
 
    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
 
    public Mandelbrot() {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                I.setRGB(x, y, iter | (iter << 8));
            }
        }
    }
 
    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
 
    public static void main(String[] args) {
        new Mandelbrot().setVisible(true);
    }
}

Proszę przetestować szybkość działania programu w zależności od liczby wątków w pul:

jeden wątek
tyle samo watkow co rdzeni
dwa razy wiecej wątkow niz rdzeni
oraz stosunku liczby zadań do liczby watkow w puli:
tyle samo zadan co wątków,
10x wiecej zadan co wątków,
każde zadanie to jeden piksel.
Czas obliczeń można zwiększać manipulując parametrami problemu, np. liczbą iteracji (MAX_ITER). Czas należy zmierzyć 10 razy dla tego samego przypadku, policzyć średnią i odchylenie standardowe. Wyniki przedstawic w tabelce, znaleźć najszybszą konfigurację.

Prosze obejrzeć na nastepne zajęcia: https://www.youtube.com/watch?v=jo_B4LTHi3I

Bibliografia

Introduction to thread pools in Java
Dokumentacja pakietu Executors
Future
CompletableFuture
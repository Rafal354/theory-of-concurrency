package tw.lab5.zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static java.lang.Math.min;

public class MandelbrotManager {
    private final int threadNumber;
    private final int taskNumber;
    private final int maxIter;
    private final int height;
    private final int width;
    private final double zoom;
    private final BufferedImage image;

    MandelbrotManager(int threadNumber, int taskNumber, int maxIter, int height, int width, double zoom) {
        this.threadNumber = threadNumber;
        this.taskNumber = taskNumber;
        this.maxIter = maxIter;
        this.height = height;
        this.width = width;
        this.zoom = zoom;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    void calculate() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
        List<Future<Integer>> codes = new ArrayList<>();

        if (taskNumber != height * width) {
            int step;
            if (height % taskNumber == 0) {
                step = height / taskNumber;
            } else {
                step = height / (taskNumber - 1);
            }
            int y1 = 0;
            int y2;

            for (int i = 0; i < taskNumber; i++) {
                y2 = min(y1 + step - 1, height - 1);
                Mandelbrot worker = new Mandelbrot(0, y1, width - 1, y2, maxIter, width, height, zoom, image);
                Future<Integer> value = executor.submit(worker);
                codes.add(value);
                y1 += step;
            }
        } else {
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++) {
                    Mandelbrot worker = new Mandelbrot(x, y, x, y, maxIter, width, height, zoom, image);
                    Future<Integer> value = executor.submit(worker);
                    codes.add(value);
                }
        }
        for (Future<Integer> value: codes) {
            value.get();
        }
        executor.shutdown();
    }
    void showImage() {
        JFrame editorFrame = new JFrame("Mandelbrot");
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(this.image);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);
    }
}

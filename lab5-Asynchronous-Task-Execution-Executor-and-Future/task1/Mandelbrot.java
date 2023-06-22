package tw.lab5.zad1;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class Mandelbrot implements Callable<Integer> {
    private final int x1, y1, x2, y2;
    private final int maxIter;
    private final double zoom;
    private final BufferedImage image;
    private final int w_2;
    private final int h_2;

    Mandelbrot(int x1, int y1, int x2, int y2,
               int maxIter, int width, int height,
               double zoom, BufferedImage image) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.maxIter = maxIter;
        this.zoom = zoom;
        this.image = image;
        this.w_2 = width / 2;
        this.h_2 = height/ 2;
    }
    @Override
    public Integer call() {
        double zx, zy, cX, cY, tmp;
        for (int y = y1; y <= y2; y++)
            for (int x = x1; x <= x2; x++) {
                int iter = maxIter;
                zx = zy = 0;
                cX = (x - w_2) / zoom;
                cY = (y - h_2) / zoom;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                image.setRGB(x, y, iter | (iter << 8));
            }
        return 0;
    }
}

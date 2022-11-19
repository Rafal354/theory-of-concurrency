package tw.lab5.zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

        int numberOfCores = Runtime.getRuntime().availableProcessors();
        System.out.println(numberOfCores);

        int[] threadsNumbers = {1, numberOfCores, 2 * numberOfCores};
        int[] maxIterations = {100, 500, 1000, 5000};
        final int rounds = 10;

        final int width = 800;
        final int height = 600;
        final int zoom = 200;

        ArrayList<Result> threadsResults = new ArrayList<>();

        for (int maxIter: maxIterations) {
            for (int threadsNumber: threadsNumbers) {
                int[] numberOfTasks = {threadsNumber, 10 * threadsNumber, width * height};
                for (int taskNumber: numberOfTasks) {
                    ArrayList<Long> times = new ArrayList<>();
                    for (int i = 0; i < rounds; i++) {
                        SingleMandelbrot singleMandelbrot = new SingleMandelbrot(
                                threadsNumber, taskNumber, maxIter, height, width, zoom
                        );
                        long startTime = System.nanoTime();
                        singleMandelbrot.calculate();
                        long endTime = System.nanoTime();
                        times.add(endTime - startTime);
                    }
                    int mean = (int) mean(times);
                    int standardDeviation = (int) standardDeviation(times, mean);
                    Result result = new Result(maxIter, threadsNumber, taskNumber, mean, standardDeviation);
                    threadsResults.add(result);
                }
            }
        }
        for (Result result: threadsResults) {
            result.show();
        }
        Excel excel = new Excel(threadsResults);
        excel.toExcel();
    }
    private static double mean(List<Long> times) {
        double sum = 0.0;
        for (Long number: times) {
            sum += number;
        }
        return times.isEmpty() ? 0 : sum / times.size();
    }
    private static double standardDeviation(List<Long> times, double mean) {
        double std = 0.0;
        for (Long number: times) {
            std += Math.pow(number - mean, 2);
        }
        return times.isEmpty() ? 0 : Math.sqrt(std / times.size());
    }
}

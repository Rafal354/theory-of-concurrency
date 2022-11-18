package tw.lab5.zad1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {


        int numberOfCores = Runtime.getRuntime().availableProcessors();

        System.out.println(numberOfCores);

        int[] threadsNumbers = {1, numberOfCores, 2 * numberOfCores};
//        int[] maxIterations = {100, 500, 1000, 5000, 10000};
        int[] maxIterations = {100};

        final int rounds = 10;
        final int width = 500;
        final int height = 500;
        final int zoom = 150;

        ArrayList<Result> threadsResults = new ArrayList<>();

        for (int maxIter: maxIterations) {
            for (int threadsNumber: threadsNumbers) {
                int[] numberOfTasks = {threadsNumber, 10 * threadsNumber, width * height};
                for (int taskNumber: numberOfTasks) {
                    ArrayList<Long> times = new ArrayList<>();
                    for (int i = 0; i < rounds; i++) {
                        SingleMandelbrot singleMandelbrot = new SingleMandelbrot(threadsNumber, taskNumber, maxIter, height, width, zoom);
                        long startTime = System.nanoTime();
                        singleMandelbrot.simulate();
                        times.add(System.nanoTime() - startTime);
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
//        System.out.println(threadsResults);
    }
    private static double mean(List<Long> times) {
        if (times.isEmpty()) {
            return 0;
        }
        double sum = 0.0;
        for(Long num: times) {
            sum += num;
        }
        return sum / times.size();
    }
    private static double standardDeviation(List<Long> times, double mean) {
        if (times.isEmpty()) {
            return 0;
        }
        double standardDeviation = 0.0;
        for(Long num: times) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation / times.size());
    }
}

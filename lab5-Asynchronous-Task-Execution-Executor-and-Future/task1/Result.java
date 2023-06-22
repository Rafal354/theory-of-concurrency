package tw.lab5.zad1;

public class Result {
    private final int maxIter;
    private final int threadNo;
    private final int taskNo;
    private final int avgTime;
    private final int stDev;

    Result(int maxIter, int threadNo, int taskNo, int avgTime, int stDev) {
        this.maxIter = maxIter;
        this.threadNo = threadNo;
        this.taskNo = taskNo;
        this.avgTime = avgTime;
        this.stDev = stDev;
    }
    public void show() {
        System.out.println("maxIter: " + maxIter + " threadNo: " + threadNo + " taskNo: " + taskNo + " avgTime: " + avgTime + " stdDev: " + stDev);
    }
    public int getMaxIter() {
        return maxIter;
    }

    public int getThreadNo() {
        return threadNo;
    }

    public int getTaskNo() {
        return taskNo;
    }

    public int getAvgTime() {
        return avgTime;
    }

    public int getStDev() {
        return stDev;
    }
}

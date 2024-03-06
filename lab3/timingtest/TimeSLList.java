package timingtest;

import ucb.util.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> Ns = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        AList<Integer> opCounts = new AList<Integer>();
        int testSLListLength = 1000;
        for (int p = 0; p < 8; p++) {
            if (p > 0) {
                testSLListLength *= 2;
            }
            Ns.addLast(testSLListLength);
            SLList<Integer> testSLList = new SLList<Integer>();
            for (int i = 0; i < testSLListLength; i++) {
                testSLList.addLast(0);
            }
            int M = 10000;
            Stopwatch sw = new Stopwatch();
            sw.start();
            for (int j = 0; j < M; j++) {
                testSLList.getLast();
            }
            double timeInSeconds = sw.getElapsed();
            times.addLast(timeInSeconds);
            opCounts.addLast(M);
        }
        printTimingTable(Ns, times, opCounts);
    }
}

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
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        AList<Integer> opCounts = new AList<Integer>();

        int N = 1000;
        int M = 10000;
        int test_times = 1;
        int timing_table_length = 8;
        for(int i = 0; i < timing_table_length; i++){
            // create an SLList
            SLList<Integer> testList = new SLList<Integer>();
            // Add N items to the SLList
            for(int j = 0; j < N; j ++){
                testList.addLast(0);
            }
            // Start the timer
            Stopwatch sw = new Stopwatch();
            sw.start();
            // add test a list
            for(int h = 0; h < test_times; h++) {
                for (int op = 0; op < M; op++) {
                    testList.getLast();
                }
                testList = new SLList<Integer>();
            }
            double sum_timeInSeconds = sw.getElapsed();
            double timeInSeconds = sum_timeInSeconds / (M * test_times);
            Ns.addLast(N);
            times.addLast(timeInSeconds);
            opCounts.addLast(M);

            N *= 2;
        }
        // Print timing table
        printTimingTable(Ns, times, opCounts);
    }

}

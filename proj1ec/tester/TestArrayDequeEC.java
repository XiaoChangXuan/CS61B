package tester;

import static org.junit.Assert.*;
import org.junit.Test;
import student.StudentArrayDeque;
import edu.princeton.cs.algs4.StdRandom;

public class TestArrayDequeEC {
    @Test
    public void testArrayDeque() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();
        StringBuilder errMsg = new StringBuilder();

        int numOperations = 100;
        for (int i = 0; i < numOperations; i++) {
            double randNumber = StdRandom.uniform();

            if (randNumber < 0.25) {
                // addFirst
                int randVal = StdRandom.uniform(100);
                studentDeque.addFirst(randVal);
                solutionDeque.addFirst(randVal);
                errMsg.append(String.format("addFirst(%d)\n", randVal));
            } else if (randNumber < 0.5) {
                // addLast
                int randVal = StdRandom.uniform(100);
                studentDeque.addLast(randVal);
                solutionDeque.addLast(randVal);
                errMsg.append(String.format("addLast(%d)\n", randVal));
            } else if (randNumber < 0.75) {
                // removeFirst
                if (!studentDeque.isEmpty() && !solutionDeque.isEmpty()) {
                    Integer expected = solutionDeque.removeFirst();
                    Integer actual = studentDeque.removeFirst();
                    errMsg.append("removeFirst()\n");
                    assertEquals(errMsg.toString(), expected, actual);
                }
            } else {
                // removeLast
                if (!studentDeque.isEmpty() && !solutionDeque.isEmpty()) {
                    Integer expected = solutionDeque.removeLast();
                    Integer actual = studentDeque.removeLast();
                    errMsg.append("removeLast()\n");
                    assertEquals(errMsg.toString(), expected, actual);
                }
            }
        }
    }
}
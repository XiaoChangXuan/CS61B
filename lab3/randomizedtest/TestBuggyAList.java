package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testTreeAddThreeRemove(){
        AListNoResizing<Integer> noResizingList = new AListNoResizing<Integer>();
        BuggyAList<Integer> buggyList = new BuggyAList<Integer>();

        // Adding values to both lists
        noResizingList.addLast(4);
        buggyList.addLast(4);
        noResizingList.addLast(5);
        buggyList.addLast(5);
        noResizingList.addLast(6);
        buggyList.addLast(6);

        // Removing and checking values
        int noResizingResult1 = noResizingList.removeLast();
        int buggyResult1 = buggyList.removeLast();
        assertEquals(noResizingResult1, buggyResult1);

        int noResizingResult2 = noResizingList.removeLast();
        int buggyResult2 = buggyList.removeLast();
        assertEquals(noResizingResult2, buggyResult2);

        int noResizingResult3 = noResizingList.removeLast();
        int buggyResult3 = buggyList.removeLast();
        assertEquals(noResizingResult3, buggyResult3);
    }
    @Test
    public void testLargeAddRemove() {
        AListNoResizing<Integer> noResizingList = new AListNoResizing<Integer>();
        BuggyAList<Integer> buggyList = new BuggyAList<Integer>();

        int n = 100; // Number of elements to add

        // Add elements to both lists
        for (int i = 0; i < n; i++) {
            noResizingList.addLast(i);
            buggyList.addLast(i);
        }

        // Remove and compare elements
        for (int i = 0; i < n; i++) {
            int noResizingRemoved = noResizingList.removeLast();
            int buggyRemoved = buggyList.removeLast();
            assertEquals(noResizingRemoved, buggyRemoved);
        }
    }
    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> noResizingList = new AListNoResizing<Integer>();
        BuggyAList<Integer> buggyList = new BuggyAList<Integer>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);

            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                noResizingList.addLast(randVal);
                buggyList.addLast(randVal);
            }else if (operationNumber == 1 && noResizingList.size() > 0) {
                // getLast
                int lastItem = noResizingList.getLast();
                int lastItem_debug = buggyList.getLast();
                assertEquals(lastItem, lastItem_debug);

            } else if (operationNumber == 2 && noResizingList.size() > 0) {
                // removeLast
                int removedItem = noResizingList.removeLast();
                int removedItem_debug = buggyList.removeLast();
                assertEquals(removedItem, removedItem_debug);
            }
        }
    }
}

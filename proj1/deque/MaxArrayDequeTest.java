package deque;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Comparator;

public class MaxArrayDequeTest {

    @Test
    public void testMax() {
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<Integer>(new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a.compareTo(b);
            }
        });

        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(15);
        deque.addLast(5);

        assertEquals(20, (int) deque.max());
    }


    @Test
    public void testMaxWithEmptyDeque() {
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<Integer>(new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a.compareTo(b);
            }
        });

        assertNull(deque.max());
    }

    @Test
    public void testMaxWithComparatorAndEmptyDeque() {
        Comparator<Integer> reverseComparator = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return b.compareTo(a);
            }
        };

        MaxArrayDeque<Integer> deque = new MaxArrayDeque<Integer>(reverseComparator);

        assertNull(deque.max(reverseComparator));
    }
}

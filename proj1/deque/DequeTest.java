package deque;
import org.junit.Test;
import static org.junit.Assert.*;

public class DequeTest {
    @Test
    public void testEqualsWithIntegers() {
        ArrayDeque<Integer> arrayDeque1 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> arrayDeque2 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> arrayDeque3 = new ArrayDeque<Integer>();

        LinkedListDeque<Integer> linkedListDeque1 = new LinkedListDeque<Integer>();
        LinkedListDeque<Integer> linkedListDeque2 = new LinkedListDeque<Integer>();
        LinkedListDeque<Integer> linkedListDeque3 = new LinkedListDeque<Integer>();

        // Add elements to ArrayDeque and LinkedListDeque
        arrayDeque1.addFirst(1);
        arrayDeque1.addLast(2);

        arrayDeque2.addFirst(1);
        arrayDeque2.addLast(2);

        arrayDeque3.addFirst(1);
        arrayDeque3.addLast(3);

        linkedListDeque1.addFirst(1);
        linkedListDeque1.addLast(2);

        linkedListDeque2.addFirst(1);
        linkedListDeque2.addLast(2);

        linkedListDeque3.addFirst(1);
        linkedListDeque3.addLast(3);

        // Test ArrayDeque equals
        assertTrue(arrayDeque1.equals(arrayDeque2));  // Same elements, same order
        assertFalse(arrayDeque1.equals(arrayDeque3)); // Different elements

        // Test LinkedListDeque equals
        assertTrue(linkedListDeque1.equals(linkedListDeque2));  // Same elements, same order
        assertFalse(linkedListDeque1.equals(linkedListDeque3)); // Different elements

        // Test ArrayDeque and LinkedListDeque equality
        assertTrue(arrayDeque1.equals(linkedListDeque1));  // Same elements, same order
        assertFalse(arrayDeque1.equals(linkedListDeque3)); // Different elements
    }
}

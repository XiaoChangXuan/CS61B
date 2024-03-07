package deque;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

public class ArrayDequeTest {

    @Test
    /* Adds a few things to the deque, checking isEmpty() and size() are correct,
      finally printing the results. */
    public void addIsEmptySizeTest() {

        ArrayDeque<String> deque = new ArrayDeque<String>();
        assertTrue("A newly initialized ArrayDeque should be empty", deque.isEmpty());
        assertEquals(0, deque.size());

        deque.addFirst("front");
        assertFalse("Deque should not be empty after adding an item", deque.isEmpty());
        assertEquals(1, deque.size());

        deque.addLast("middle");
        deque.addLast("back");
        assertEquals(3, deque.size());

        System.out.println("Printing out deque: ");
        deque.printDeque();
    }

    @Test
    /* Adds an item, then removes an item, and ensures that deque is empty afterwards. */
    public void addRemoveTest() {

        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        assertTrue("Deque should be empty upon initialization", deque.isEmpty());

        deque.addFirst(10);
        assertFalse("Deque should not be empty after adding an item", deque.isEmpty());

        deque.removeFirst();
        assertTrue("Deque should be empty after removal", deque.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        deque.addFirst(3);

        deque.removeLast();
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();

        assertEquals("Size should be 0 when removing from empty deque.", 0, deque.size());
    }

    @Test
    /* Check if you can create ArrayDeque with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String> deque1 = new ArrayDeque<String>();
        ArrayDeque<Double> deque2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> deque3 = new ArrayDeque<Boolean>();

        deque1.addFirst("string");
        deque2.addFirst(3.14159);
        deque3.addFirst(true);

        assertEquals("string", deque1.removeFirst());
        assertEquals(3.14159, deque2.removeFirst(), 0.0);
        assertTrue(deque3.removeFirst());
    }

    @Test
    /* Check if null is returned when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {

        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        assertNull("Should return null when removeFirst is called on an empty Deque,", deque.removeFirst());
        assertNull("Should return null when removeLast is called on an empty Deque,", deque.removeLast());
    }

    @Test
    /* Add a large number of elements to deque; check if order is correct. */
    public void bigArrayDequeTest() {

        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            deque.addLast(i);
        }

        for (int i = 0; i < 500000; i++) {
            assertEquals(i, (int) deque.removeFirst());
        }

        for (int i = 999999; i > 500000; i--) {
            assertEquals(i, (int) deque.removeLast());
        }
    }

    @Test
    /* Check if elements are added and removed in correct order */
    public void orderTest() {
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        deque.addLast(1);
        deque.addFirst(2);
        deque.addLast(3);

        assertEquals(2, (int) deque.removeFirst());
        assertEquals(1, (int) deque.removeFirst());
        assertEquals(3, (int) deque.removeFirst());
        assertTrue(deque.isEmpty());
    }

    @Test
    /* Check if elements are added and removed correctly when resizing */
    public void resizeTest() {
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000; i++) {
            deque.addLast(i);
        }

        for (int i = 0; i < 990; i++) {
            deque.removeFirst();
        }

        for (int i = 0; i < 500; i++) {
            deque.addLast(i);
        }

        assertEquals(990, (int) deque.removeFirst());
        assertEquals(509, deque.size());
    }
    @Test
    public void iteratorTest() {
        // 创建一个 ArrayDeque 对象
        ArrayDeque<String> deque = new ArrayDeque<String>();

        // 添加一些元素
        deque.addFirst("Apple");
        deque.addFirst("Peach");
        deque.addLast("Banana");
        deque.addLast("Cherry");

        // 使用迭代器遍历并打印元素
        System.out.println("Using iterator to print elements:");
        Iterator<String> iterator = deque.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println(element);
        }
    }

    @Test
    public void equalTest() {
        ArrayDeque<String> deque1 = new ArrayDeque<String>();
        ArrayDeque<Integer> deque2 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> deque3 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> deque4 = new ArrayDeque<Integer>();

        deque1.addFirst("string");

        deque2.addFirst(5);
        deque2.addFirst(3);

        deque3.addFirst(5);
        deque3.addFirst(3);

        deque4.addFirst(3);
        deque4.addFirst(5);

        assertFalse(deque1.equals(deque2));
        assertTrue(deque2.equals(deque3));
        assertFalse(deque2.equals(deque4));
    }

    @Test
    public void testEqualsWithStrings() {
        ArrayDeque<String> deque1 = new ArrayDeque<String>();
        ArrayDeque<String> deque2 = new ArrayDeque<String>();
        ArrayDeque<String> deque3 = new ArrayDeque<String>();

        deque1.addFirst("Hello");
        deque1.addLast("World");

        deque2.addFirst("Hello");
        deque2.addLast("World");

        deque3.addFirst("Goodbye");
        deque3.addLast("World");

        assertTrue(deque1.equals(deque2));  // Same elements, same order
        assertFalse(deque1.equals(deque3)); // Different elements
    }

    @Test
    public void testEqualsWithIntegers() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> deque2 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> deque3 = new ArrayDeque<Integer>();

        deque1.addFirst(1);
        deque1.addLast(2);

        deque2.addFirst(1);
        deque2.addLast(2);

        deque3.addFirst(1);
        deque3.addLast(3);

        assertTrue(deque1.equals(deque2));  // Same elements, same order
        assertFalse(deque1.equals(deque3)); // Different elements
    }


}

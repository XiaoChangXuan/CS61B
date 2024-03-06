package deque;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /* Adds a few things to the list, checking isEmpty() and size() are correct,
      finally printing the results.

      && is the "and" operation. */
    public void addIsEmptySizeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();
    }

    @Test
    /* Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeque with different parameterized types*/
    public void multipleParamTest() {


        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
        assertEquals("string", s);
        assertTrue(b);

    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertNull("Should return null when removeFirst is called on an empty Deque,", lld1.removeFirst());
        assertNull("Should return null when removeLast is called on an empty Deque,", lld1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }
    @Test
    /* Check if you can create LinkedListDeque with different parameterized types*/
    public void iteratorTest() {
        // 创建一个 LinkedListDeque 对象
        LinkedListDeque<String> deque = new LinkedListDeque<String>();

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
    /* Check if you can create LinkedListDeque with different parameterized types*/
    public void equalTest() {
        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<Integer>();
        LinkedListDeque<Integer> lld3 = new LinkedListDeque<Integer>();
        LinkedListDeque<Integer> lld4 = new LinkedListDeque<Integer>();

        lld1.addFirst("string");

        lld2.addFirst(5);
        lld2.addFirst(3);

        lld3.addFirst(5);
        lld3.addFirst(3);

        lld4.addFirst(3);
        lld4.addFirst(5);

        assertFalse(lld1.equals(lld2));
        assertTrue(lld2.equals(lld3));
        assertFalse(lld2.equals(lld4));
    }
    @Test
    public void testGet() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<Integer>();
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);

        // Test valid indices
        assertEquals(10, (int) deque.get(0));
        assertEquals(20, (int) deque.get(1));
        assertEquals(30, (int) deque.get(2));

        // Test invalid indices
        assertNull(deque.get(-1));
        assertNull(deque.get(3));
        assertNull(deque.get(10));

        // Test get on empty deque
        LinkedListDeque<String> emptyDeque = new LinkedListDeque<String>();
        assertNull(emptyDeque.get(0));
    }

    @Test
    public void testGetRecursive() {
        LinkedListDeque<String> deque = new LinkedListDeque<String>();
        deque.addLast("Apple");
        deque.addLast("Banana");
        deque.addLast("Cherry");

        // Test valid indices
        assertEquals("Apple", deque.getRecursive(0));
        assertEquals("Banana", deque.getRecursive(1));
        assertEquals("Cherry", deque.getRecursive(2));

        // Test invalid indices
        assertNull(deque.getRecursive(-1));
        assertNull(deque.getRecursive(3));
        assertNull(deque.getRecursive(10));

        // Test getRecursive on empty deque
        LinkedListDeque<Integer> emptyDeque = new LinkedListDeque<Integer>();
        assertNull(emptyDeque.getRecursive(0));
    }
}

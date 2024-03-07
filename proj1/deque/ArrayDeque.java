package deque;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Objects;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int front;
    private int rear;

    private static final int INITIAL_CAPACITY = 8;
    private static final double MIN_USAGE_FACTOR = 0.25;

    /** Creates an empty array deque */
    public ArrayDeque() {
        items = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        front = 0;
        rear = 0;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        front = (front - 1 + items.length) % items.length;
        items[front] = item;
        size++;
    }
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[rear] = item;
        rear = (rear + 1) % items.length;
        size++;
    }
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            System.out.println("Deque is empty.");
            return null;
        }
        T removedItem = items[front];
        items[front] = null;
        front = (front + 1) % items.length;
        size--;
        if (size > 0 && size < items.length * MIN_USAGE_FACTOR) {
            resize(items.length / 2);
        }
        return removedItem;
    }
    @Override
    public T removeLast() {
        if (isEmpty()) {
            System.out.println("Deque is empty.");
            return null;
        }
        rear = (rear - 1 + items.length) % items.length;
        T removedItem = items[rear];
        items[rear] = null;
        size--;
        if (size > 0 && size < items.length * MIN_USAGE_FACTOR) {
            resize(items.length / 2);
        }
        return removedItem;
    }
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return items[(front + index) % items.length];
    }
    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[(front + i) % items.length] + " ");
        }
        System.out.println();
    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T> {
        private int currentIndex = 0;

        public boolean hasNext() {
            return currentIndex < size;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = items[(front + currentIndex) % items.length];
            currentIndex++;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Deque<?>)) {
            return false;
        }
        Deque<?> other = (Deque<?>) o;
        if (other.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            T thisItem = this.get(i);
            Object otherItem = other.get(i);
            if (!(Objects.equals(thisItem, otherItem))) {
                return false;
            }
        }
        return true;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[(front + i) % items.length];
        }
        items = newItems;
        front = 0;
        rear = size;
    }
}

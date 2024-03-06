package deque;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>{
    private class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    //front is head_node, front.next is real node
    private Node front;
    private Node rear;
    private int size;

    /** Creates an empty deque*/
    public LinkedListDeque(){
        front = new Node(null, null,null);
        rear = front;
        size = 0;
    }
    public LinkedListDeque(T x){
        front = new Node(null, null,null);
        rear = new Node(x, front,null);
        front.next = rear;
        size = 1;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public T get(int index){
        // return index_val if index is valid,else return null;
        if (index < 0 || index >= size) {
            return null;  // 索引超出范围，返回 null
        }
        Node current = front.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.item;
    }
    public T getRecursive(int index) {
        return getRecursiveHelper(front.next, index);
    }

    private T getRecursiveHelper(Node current, int index) {
        if (current == null || index < 0) {
            return null;  // 索引超出范围，返回 null
        }
        if (index == 0) {
            return current.item;  // 当 index 为 0 时返回当前节点的值
        }
        return getRecursiveHelper(current.next, index - 1);
    }
    @Override
    public void addFirst(T x) {
        Node temp_node = new Node(x, front, front.next);
        if(this.isEmpty()){
            rear = temp_node;
        }
        else{
            front.next.prev = temp_node;
        }
        front.next = temp_node;
        size++;
    }
    @Override
    public void addLast(T x){
        Node temp_node = new Node(x, rear, null);
        rear.next = temp_node;
        rear = temp_node;
        if(this.isEmpty()){
            rear = temp_node;
        }
        size++;
    }
    @Override
    public T removeFirst(){
        if(this.isEmpty()){
            System.out.println("False, lst is empty");
            return null;
        }
        T item = front.next.item;
        if (this.size() == 1) {
            front.next = null;
            rear = front;
        } else {
            front.next = front.next.next;
            front.next.prev = front;
        }
        size--;
        return item;
    }
    @Override
    public T removeLast() {
        if (isEmpty()) {
            System.out.println("False, lst is empty");
            return null;
        }
        T item = rear.item;
        if (size() == 1) {
            front.next = null;
            rear = front;
        } else {
            rear.prev.next = null;
            rear = rear.prev;
        }
        size--;
        return item;
    }
    @Override
    public void printDeque() {
        Node cur = front.next;
        while (cur != null) {
            System.out.print(cur.item + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<T> {
        private Node current = front.next;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }
        LinkedListDeque<?> other = (LinkedListDeque<?>) o;
        if (this.size() != other.size()) {
            return false;
        }
        Iterator<T> thisIterator = this.iterator();
        Iterator<?> otherIterator = other.iterator();
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            T thisElement = thisIterator.next();
            Object otherElement = otherIterator.next();
            if (!(thisElement == null ? otherElement == null : thisElement.equals(otherElement))) {
                return false;
            }
        }
        return true;
    }
}

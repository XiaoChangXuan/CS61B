package deque;

public class LinkedListDeque<Item> {
    private class Node {
        public Item item;
        public Node prev;
        public Node next;

        public Node(Item i, Node pre_node, Node next_node) {
            item = i;
            prev = pre_node;
            next = next_node;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    /** Creates an empty deque*/
    public LinkedListDeque(){
        front = new Node(null, null,null);
        rear = front;
        size = 0;
    }
    public LinkedListDeque(Item x){
        front = new Node(null, null,null);
        rear = new Node(x, front,null);
        front.next = rear;
        size = 1;
    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }

    public void addFirst(Item x){
        Node temp_node = new Node(x, front, front.next);
        front.next = temp_node;
        if(temp_node.next != null){
            temp_node.next.front = temp_node;
        }
        if(size == 0){
            rear = temp_node;
        }
        size += 1;
    }

    public void addLast(Item x){
        Node temp_node = new Node(x, rear, null);
        rear.next = temp_node;
        rear = temp_node;
    }

    public void printDeque(){
        Node cur_node = front.next;
        while (cur_node != null){

        }
    }

}

package deque;
import java.util.Comparator;

public class MaxArrayDeque<Item> extends ArrayDeque<Item> {
    private Comparator<Item> comparator;

    public MaxArrayDeque(Comparator<Item> c) {
        comparator = c;
    }

    public Item max() {
        if (isEmpty()) {
            return null;
        }
        Item maxItem = get(0);
        for (int i = 1; i < size(); i++) {
            Item current = get(i);
            if (comparator.compare(current, maxItem) > 0) {
                maxItem = current;
            }
        }
        return maxItem;
    }

    public Item max(Comparator<Item> c) {
        if (isEmpty()) {
            return null;
        }
        Item maxItem = get(0);
        for (int i = 1; i < size(); i++) {
            Item current = get(i);
            if (c.compare(current, maxItem) > 0) {
                maxItem = current;
            }
        }
        return maxItem;
    }
}
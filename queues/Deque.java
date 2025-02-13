import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node sentinel;
    private int size;

    // construct an empty deque
    public Deque() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    // is the deque empty?
    public boolean isEmpty() {}

    // return the number of items on the deque
    public int size() {}

    // add the item to the front
    public void addFirst(Item item) {
        validateItem(item);
    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem(item);
    }

    private void validateItem(Item item) {
        if (item == null) {
            String error = "Item cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateDeque();
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateDeque();
    }

    private void validateDeque() {
        if (isEmpty()) {
            String error = "Deque is empty";
            throw new NoSuchElementException(error);
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    // unit testing (required)
    public static void main(String[] args) {}

    private class DequeIterator implements Iterator<Item> {
        public boolean hasNext() {}

        public Item next() {}

        public void remove() {
            String error = "Remove operation is not supported";
            throw new UnsupportedOperationException(error);
        }
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item data, Node nextNode, Node prevNode) {
            item = data;
            next = nextNode;
            prev = prevNode;
        }
    }
}

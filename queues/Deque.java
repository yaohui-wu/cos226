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

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateItem(item);
        Node first = new Node(item, sentinel.next, sentinel);
        first.next.prev = first;
        sentinel.next = first;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem(item);
        Node last = new Node(item, sentinel, sentinel.prev);
        last.prev.next = last;
        last.next = sentinel;
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
        Item item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateDeque();
        Item item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return item;
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

    private class DequeIterator implements Iterator<Item> {
        private Node curr = sentinel.next;

        public boolean hasNext() {
            return curr != sentinel;
        }

        public Item next() {
            Item item = curr.item;
            curr = curr.next;
            return item;
        }

        public void remove() {
            String error = "Remove operation is not supported";
            throw new UnsupportedOperationException(error);
        }
    }

    // unit testing (required)
    public static void main(String[] args) {}
}

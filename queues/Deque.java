import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Implementation of a double-ended queue (deque) using a circular doubly
 * linked list with a sentinel node.
 * 
 * @author Yaohui Wu
 */
public class Deque<Item> implements Iterable<Item> {
    private int size; // Number of items in the deque.
    private Node sentinel; // Sentinel node of the doubly linked list.

    /**
     * Constructs an empty deque with a sentinel node.
     */
    public Deque() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /**
     * Node class for the doubly linked list.
     */
    private class Node {
        private Item item; // Data of the node.
        private Node next; // Reference to the next node.
        private Node prev; // Reference to the previous node.

        public Node(Item data, Node nextNode, Node prevNode) {
            item = data;
            next = nextNode;
            prev = prevNode;
        }
    }

    /**
     * Checks if the deque is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Adds an item to the front of the deque.
     */
    public void addFirst(Item item) {
        validateItem(item);
        Node first = new Node(item, sentinel.next, sentinel);
        first.next.prev = first;
        sentinel.next = first;
    }

    /**
     * Adds an item to the back of the deque.
     */
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
    /**
     * Removes and returns the item from the front of the deque.
     */
    public Item removeFirst() {
        validateDeque();
        Item item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return item;
    }

    /**
     * Removes and returns the item from the back of the deque.
     */
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

    /**
     * Returns an iterator over the items in the deque from front to back.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node curr = sentinel.next;

        public boolean hasNext() {
            return curr != sentinel;
        }

        public Item next() {
            validateNext();
            Item item = curr.item;
            curr = curr.next;
            return item;
        }
        
        private void validateNext() {
            if (!hasNext()) {
                String error = "No more items to iterate";
                throw new NoSuchElementException(error);
            }
        }

        public void remove() {
            String error = "Remove operation is not supported";
            throw new UnsupportedOperationException(error);
        }
    }

    // unit testing (required)
    public static void main(String[] args) {}
}

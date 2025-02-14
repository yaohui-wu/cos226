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
    // Sentinel node of the doubly linked list to track the front and back.
    private Node sentinel;

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
        // Create a new node and insert it after the sentinel node.
        Node first = new Node(item, sentinel.next, sentinel);
        // Update the references of the sentinel node and the first node.
        first.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    /**
     * Adds an item to the back of the deque.
     */
    public void addLast(Item item) {
        validateItem(item);
        // Create a new node and insert it before the sentinel node.
        Node last = new Node(item, sentinel, sentinel.prev);
        // Update the references of the sentinel node and the last node.
        last.prev.next = last;
        last.next = sentinel;
        size += 1;
    }

    private void validateItem(Item item) {
        if (item == null) {
            String error = "Item cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Removes and returns the item from the front of the deque.
     */
    public Item removeFirst() {
        validateDeque();
        Item item = sentinel.next.item;
        // Update the references of the sentinel node and the first node.
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return item;
    }

    /**
     * Removes and returns the item from the back of the deque.
     */
    public Item removeLast() {
        validateDeque();
        Item item = sentinel.prev.item;
        // Update the references of the sentinel node and the last node.
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
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
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        System.out.println("Is deque empty? " + deque.isEmpty());
        System.out.println("Size of deque: " + deque.size());
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);
        System.out.println("Is deque empty? " + deque.isEmpty());
        System.out.println("Size of deque: " + deque.size());
        System.out.println("Items in deque from front to back:");
        for (int item : deque) {
            System.out.println(item);
        }
        System.out.println("Remove first item: " + deque.removeFirst());
        System.out.println("Remove last item: " + deque.removeLast());
        System.out.println("Items in deque from front to back:");
        for (int item : deque) {
            System.out.println(item);
        }
        System.out.println("Remove first item: " + deque.removeFirst());
        System.out.println("Remove last item: " + deque.removeLast());
        System.out.println("Is deque empty? " + deque.isEmpty());
        System.out.println("Size of deque: " + deque.size());
    }
}

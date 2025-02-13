import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Implementtion of a randomized queue, a queue where the item removed is
 * chosen uniformly at random, using a dynamic array.
 * 
 * @author Yaohui Wu
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 10;
    private static final int RESIZE_FACTOR = 2;
    private static final int SHRINK_FACTOR = 4;
    private int size; // Number of items in the queue.
    private int capacity; // Capacity of the queue.
    private Item[] items;

    /**
     * Constructs an empty randomized queue.
     */
    public RandomizedQueue() {
        size = 0;
        capacity = INITIAL_CAPACITY;
        items = (Item[]) new Object[capacity];
    }

    /**
     * Checks if the queue is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the queue.
     */
    public int size() {
        return size;
    }

    /**
     * Adds an item to the front of the queue.
     */
    public void enqueue(Item item) {
        validateEnqueue(item);
        if (size == capacity) {
            resize(capacity * RESIZE_FACTOR);
        }
        items[size] = item;
        size += 1;
    }

    private void validateEnqueue(Item item) {
        if (item == null) {
            String error = "Item cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Removes and returns a random item from the queue.
     */
    public Item dequeue() {
        validateQueue();
        int index = StdRandom.uniformInt(size);
        Item item = items[index];
        items[index] = items[size - 1];
        items[size - 1] = null;
        size -= 1;
        if (size > 0 && size == capacity / SHRINK_FACTOR) {
            resize(capacity / RESIZE_FACTOR);
        }
        return item;
    }

    /**
     * Resizes the queue to the new capacity.
     */
    private void resize(int newCapacity) {
        if (newCapacity >= INITIAL_CAPACITY) {
            capacity = newCapacity;
            Item[] newItems = (Item[]) new Object[capacity];
            System.arraycopy(items, 0, newItems, 0, size);
            items = newItems;
        }
    }

    // return a random item (but do not remove it)
    /**
     * Returns a random item from the queue but does not remove it.
     */
    public Item sample() {
        validateQueue();
        int index = StdRandom.uniformInt(size);
        Item item = items[index];
        return item;
    }

    private void validateQueue() {
        if (isEmpty()) {
            String error = "Queue is empty";
            throw new NoSuchElementException(error);
        }
    }

    /**
     * Returns an iterator over the items in the queue in random order.
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        public boolean hasNext() {
            return size > 0;
        }

        public Item next() {
            validateNext();
            return dequeue();
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

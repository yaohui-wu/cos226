import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Implementation of a randomized queue, a queue where the item removed is
 * chosen uniformly at random, using a dynamic resizable array.
 * 
 * @author Yaohui Wu
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 10;
    private static final int RESIZE_FACTOR = 2;
    private static final int SHRINK_FACTOR = 4;
    private Item[] items;
    private int size; // Number of items in the queue.
    private int capacity; // Capacity of the queue.

    /**
     * Constructs an empty randomized queue.
     */
    public RandomizedQueue() {
        capacity = INITIAL_CAPACITY;
        items = (Item[]) new Object[capacity];
        size = 0;
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
        // Expand the queue if it is full.
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
        // Swap the item to be removed with the last item in the queue.
        items[index] = items[size - 1];
        // Set the item to be removed to null to avoid loitering.
        items[size - 1] = null;
        size -= 1;
        // Shrink the queue if it is one-quarter full.
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
        private int index;
        private Item[] randomItems;

        public RandomizedQueueIterator() {
            index = 0;
            randomItems = (Item[]) new Object[size];
            System.arraycopy(items, 0, randomItems, 0, size);
            StdRandom.shuffle(randomItems);
        }

        public boolean hasNext() {
            return index < size;
        }

        public Item next() {
            validateNext();
            Item item = randomItems[index];
            index += 1;
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

    /**
     * Unit tests for the RandomizedQueue data structure.
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        for (int item : queue) {
            System.out.println(item);
        }
        System.out.println("Size: " + queue.size());
        System.out.println("Sample: " + queue.sample());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Size: " + queue.size());
        System.out.println("Sample: " + queue.sample());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Size: " + queue.size());
        System.out.println("Sample: " + queue.sample());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Size: " + queue.size());
        System.out.println("Sample: " + queue.sample());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Size: " + queue.size());
        System.out.println("Sample: " + queue.sample());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Size: " + queue.size());
    }
}

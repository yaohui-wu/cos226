import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 10;
    private static final int RESIZE_FACTOR = 2;
    private static final int SHRINK_FACTOR = 4;
    private int size;
    private int capacity;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        capacity = INITIAL_CAPACITY;
        items = (Item[]) new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        validateEnqueue(item);
        if (size == capacity) {
            resize(capacity * RESIZE_FACTOR);
        }
        items[size - 1] = item;
        size += 1;
    }

    private void validateEnqueue(Item item) {
        if (item == null) {
            String error = "Item cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    private void resize(int newCapacity) {
        if (newCapacity >= INITIAL_CAPACITY) {
            capacity = newCapacity;
            Item[] newItems = (Item[]) new Object[capacity];
            System.arraycopy(items, 0, newItems, 0, size);
            items = newItems;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        validateQueue();
        int index = StdRandom.uniformInt(size);
        Item item = items[index];
        items[index] = null;
        size -= 1;
        if (size > 0 && size == capacity / SHRINK_FACTOR) {
            resize(capacity / RESIZE_FACTOR);
        }
        return item;
    }

    // return a random item (but do not remove it)
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

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        public boolean hasNext() {}

        public Item next() {
            validateNext();
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

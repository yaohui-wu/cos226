import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {}

    // add the item
    public void enqueue(Item item) {
        validateEnqueue(item);
    }

    private void validateEnqueue(Item item) {
        if (item == null) {
            String error = "Item cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        validateQueue();
    }

    // return a random item (but do not remove it)
    public Item sample() {
        validateQueue();
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

import java.util.Arrays;

public class CircularSuffixArray {
    private final int length;
    private final char[] suffixes; // Original suffixes.
    private final char[] sorted; // Sorted suffixes.

    /**
     * Circular suffix array of s.
     */
    public CircularSuffixArray(String s) {
        validateArg(s);
        length = s.length();
        suffixes = s.toCharArray();
        sorted = new char[length];
        System.arraycopy(suffixes, 0, sorted, 0, length);
        Arrays.sort(sorted);
    }

    private void validateArg(Object obj) {
        if (obj == null) {
            String error = "Argument cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Length of s.
     */
    public int length() {
        return length;
    }

    /**
     * Returns index of ith sorted suffix.
     */
    public int index(int i) {
        validateIndex(i);
    }

    private void validateIndex(int i) {
        if (i < 0 || i >= length) {
            String error = "Index out of bounds";
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Unit testing.
     */
    public static void main(String[] args) {}
}

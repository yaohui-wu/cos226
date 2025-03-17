import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

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
        char c = sorted[i];
        int index = search(suffixes, c);
        return index;
    }

    private void validateIndex(int i) {
        if (i < 0 || i >= length) {
            String error = "Index out of bounds";
            throw new IllegalArgumentException(error);
        }
    }

    private int search(char[] characters, char c) {
        int len = characters.length;
        for (int i = 0; i < len; i++) {
            if (characters[i] == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Unit testing.
     */
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        StdOut.println(s);
        int length = csa.length();
        StdOut.println("Length: " + length);
        for (int i = 0; i < length; i++) {
            int index = csa.index(i);
            StdOut.printf("Index[%d]: %d\n", i, index);
        }
    }
}

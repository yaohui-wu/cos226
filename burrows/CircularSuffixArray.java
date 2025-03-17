import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private final int length;
    private final Integer[] indices;

    /**
     * Circular suffix array of s.
     */
    public CircularSuffixArray(String s) {
        validateArg(s);
        length = s.length();
        indices = new Integer[length];
        for (int i = 0; i < length; i++) {
            indices[i] = i;
        }
        Comparator<Integer> comparator = (idx1, idx2) -> {
            int element = idx1;
            // Skip equal characters.
            while (s.charAt(idx1) == s.charAt(idx2)) {
                // Compare the next character in the circular suffix.
                idx1 = (idx1 + 1) % length;
                idx2 = (idx2 + 1) % length;
                // All characters are equal.
                if (idx1 == element) {
                    return 0;
                }
            }
            // Compare characters in ascending order.
            if (s.charAt(idx1) < s.charAt(idx2)) {
                return -1;
            } else {
                return 1;
            }
        };
        Arrays.sort(indices, comparator);
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
        return indices[i];
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

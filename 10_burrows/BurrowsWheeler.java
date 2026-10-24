import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int MAX_ASCII = 256;
    
    /**
     * Apply Burrows-Wheeler transform, reading from standard input and
     * writing to standard output.
     */
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int length = s.length();
        char[] t = new char[length];
        // The row number first in which the original string ends up.
        int first = 0;
        for (int i = 0; i < length; i++) {
            int index = csa.index(i); // The ith original suffix string.
            if (index == 0) {
                first = i;
            }
            // Index of its last character.
            int lastIndex = (length - 1 + index) % length;
            t[i] = s.charAt(lastIndex);
        }
        BinaryStdOut.write(first);
        for (char c : t) {
            BinaryStdOut.write(c);
        }
        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    /**
     * Apply Burrows-Wheeler inverse transform, reading from standard input
     * and writing to standard output.
     */
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int length = s.length();
        char[] t = s.toCharArray();
        int[] next = next(t);
        for (int i = 0; i < length; i++) {
            int index = next[first];
            BinaryStdOut.write(t[index]);
            first = index;
        }
        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    private static int[] next(char[] t) {
        int length = t.length;
        // Compute the frequency counts.
        int[] counts = new int[MAX_ASCII + 1];
        for (int i = 0; i < length; i++) {
            int index = t[i] + 1;
            counts[index] += 1;
        }
        // Transform counts to indices.
        for (int i = 0; i < MAX_ASCII - 1; i++) {
            counts[i + 1] += counts[i];
        }
        // Construct the array next[].
        int[] next = new int[t.length];
        for (int i = 0; i < length; i++) {
            int c = (int) t[i];
            int index = counts[c];
            next[index] = i;
            counts[c] += 1;
        }
        return next;
    }

    /**
     * If args[0] is "-", apply Burrows-Wheeler transform. If args[0] is "+",
     * apply Burrows-Wheeler inverse transform.
     */
    public static void main(String[] args) {
        String arg = args[0];
        if (arg.equals("-")) {
            transform();
        } else if (arg.equals("+")) {
            inverseTransform();
        }
    }
}

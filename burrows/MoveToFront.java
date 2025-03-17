import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int MAX_ASCII = 256;
    private static final int BITS = 8;

    /**
     * Apply move-to-front encoding, reading from standard input and writing
     * to standard output.
     */
    public static void endcode() {
        char[] seq = ascii_sequence();
        while (!BinaryStdIn.isEmpty()) {
            // Read an 8-bit character.
            char c = BinaryStdIn.readChar();
            boolean found = false;
            int i = 0;
            while (!found || i < MAX_ASCII) {
                if (seq[i] == c) {
                    found = true;
                    // Print the 8-bit index.
                    BinaryStdOut.write(i, BITS);
                    // Move the character to the front of the sequence.
                    moveToFront(seq, i);
                }
                i += 1;
            }
        }
        BinaryStdOut.close();
    }

    /**
     * Apply move-to-front decoding, reading from standard input and writing
     * to standard output.
     */
    public static void decode() {
        char[] seq = ascii_sequence();
        while (!BinaryStdIn.isEmpty()) {
            // Read an 8-bit index.
            int i = (int) BinaryStdIn.readChar();
            // Print the character at index i.
            BinaryStdOut.write(seq[i]);
            // Move the character to the front of the sequence.
            moveToFront(seq, i);
        }
        BinaryStdOut.close();
    }

    /**
     * Initialize an ordered sequence of 256 characters, where the ith
     * character in the sequence equal to the ith extended ASCII character.
     */
    private static char[] ascii_sequence() {
        char[] seq = new char[MAX_ASCII];
        for (int i = 0; i < MAX_ASCII; i++) {
            seq[i] = (char) i;
        }
        return seq;
    }

    /**
     * Move the character at index i to the front of the sequence.
     */
    private static void moveToFront(char[] seq, int i) {
        char c = seq[i];
        // Shift the characters to the right.
        for (int j = i; j > 0; j--) {
            seq[j] = seq[j - 1];
        }
        // Move the character to the front.
        seq[0] = c;
    }

    /**
     * If args[0] is "-", apply move-to-front encoding. If args[0] is "+",
     * apply move-to-front decoding.
     */
    public static void main(String[] args) {
        String arg = args[0];
        if (arg.equals("-")) {
            endcode();
        } else if (arg.equals("+")) {
            decode();
        }
    }
}

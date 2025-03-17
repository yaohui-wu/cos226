import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    
    /**
     * Apply Burrows-Wheeler transform, reading from standard input and
     * writing to standard output.
     */
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int length = s.length();
        char[] t = new char[length];
        int first = 0;
        for (int i = 0; i < length; i++) {
            int index = csa.index(i);
            if (index == 0) {
                first = i;
            }
            int lastIndex = (index - 1 + length) % length;
            t[i] = s.charAt(lastIndex);
        }
        BinaryStdOut.write(first);
        for (char c : t) {
            BinaryStdOut.write(c);
        }
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
        BinaryStdOut.close();
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

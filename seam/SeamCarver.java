import edu.princeton.cs.algs4.Picture;

/**
 * Seam carving.
 * 
 * @author Yaohui Wu
 */
public class SeamCarver {
    private int width;
    private int height;

    /**
     * Create a seam carver object based on the given picture.
     */
    public SeamCarver(Picture picture) {
        validateArg(picture);
    }

    private void validateArg(Object arg) {
        if (arg == null) {
            String error = "Argument cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Current picture.
     */
    public Picture picture() {}

    /**
     * Width of current picture.
     */
    public int width() {}

    /**
     * Height of current picture.
     */
    public int height() {}

    /**
     * Energy of pixel at column x and row y.
     */
    public double energy(int x, int y) {
        validateIndices(x, y);
    }

    private void validateIndices(int x, int y) {
        StringBuilder error = new StringBuilder();
        if (x < 0 || x >= width) {
            error.append("x must be between 0 and ");
            error.append(width - 1);
        } else if (y < 0 || y >= height) {
            error.append("y must be between 0 and ");
            error.append(height - 1);
        }
        if (!error.isEmpty()) {
            throw new IllegalArgumentException(error.toString());
        }
    }

    /**
     * Sequence of indices for horizontal seam.
     */
    public int[] findHorizontalSeam() {}

    /**
     * Sequence of indices for vertical seam.
     */
    public int[] findVerticalSeam() {}

    /**
     * Remove horizontal seam from current picture.
     */
    public void removeHorizontalSeam(int[] seam) {
        validateArg(seam);
        validateHorizontalSeam(seam);
    }

    private void validateHorizontalSeam(int[] seam) {
        StringBuilder error = new StringBuilder();
        int length = seam.length;
        if (length != width) {
            error.append("Horizontal seam must have length ");
            error.append(width);
            throw new IllegalArgumentException(error.toString());
        }
        for (int i = 0; i < length - 1; i++) {
            int entry = seam[i];
            if (entry < 0 || entry >= height) {
                error.append("Entry ");
                error.append(entry);
                error.append(" at index ");
                error.append(i);
                error.append(" is out of bounds");
                throw new IllegalArgumentException(error.toString());
            } else if (Math.abs(entry - seam[i + 1]) > 1) {
                error.append("Adjacent entries ");
                error.append(entry);
                error.append(" and ");
                error.append(seam[i + 1]);
                error.append(" differ by more than 1");
                throw new IllegalArgumentException(error.toString());
            }
        }
    }

    /**
     * Remove vertical seam from current picture.
     */
    public void removeVerticalSeam(int[] seam) {
        validateArg(seam);
        validateVerticalSeam(seam);
    }

    private void validateVerticalSeam(int[] seam) {
        StringBuilder error = new StringBuilder();
        int length = seam.length;
        if (length != height) {
            error.append("Vertical seam must have length ");
            error.append(height);
            throw new IllegalArgumentException(error.toString());
        }
        for (int i = 0; i < length - 1; i++) {
            int entry = seam[i];
            if (entry < 0 || entry >= width) {
                error.append("Entry ");
                error.append(entry);
                error.append(" at index ");
                error.append(i);
                error.append(" is out of bounds");
                throw new IllegalArgumentException(error.toString());
            } else if (Math.abs(entry - seam[i + 1]) > 1) {
                error.append("Adjacent entries ");
                error.append(entry);
                error.append(" and ");
                error.append(seam[i + 1]);
                error.append(" differ by more than 1");
                throw new IllegalArgumentException(error.toString());
            }
        }
    }


    /**
     * Unit testing.
     */
    public static void main(String[] args) {}
}

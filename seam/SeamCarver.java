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
        String error = null;
        if (x < 0 || x >= width) {
            error = "x must be between 0 and " + (width - 1);
        } else if (y < 0 || y >= height) {
            error = "y must be between 0 and " + (height - 1);
        }
        if (error != null) {
            throw new IllegalArgumentException(error);
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
    }

    /**
     * Remove vertical seam from current picture.
     */
    public void removeVerticalSeam(int[] seam) {
        validateArg(seam);
    }

    /**
     * Unit testing.
     */
    public static void main(String[] args) {}
}

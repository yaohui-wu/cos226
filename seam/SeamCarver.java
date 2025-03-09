import java.awt.Color;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

/**
 * Seam carving.
 * 
 * @author Yaohui Wu
 */
public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;

    /**
     * Create a seam carver object based on the given picture.
     */
    public SeamCarver(Picture picture) {
        validateArg(picture);
        this.picture = new Picture(picture);
        width = this.picture.width();
        height = this.picture.height();
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
    public Picture picture() {
        return new Picture(picture);
    }

    /**
     * Width of current picture.
     */
    public int width() {
        return width;
    }

    /**
     * Height of current picture.
     */
    public int height() {
        return height;
    }

    /**
     * Energy of pixel at column x and row y.
     */
    public double energy(int x, int y) {
        validateIndices(x, y);
        // Define the energy of a pixel at the border of the image to be 1,000.0.
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
            return 1_000.0;
        }
        // Dual-gradient energy function.
        double xGrad = gradient(x + 1, y, x - 1, y); // Square x-gradient.
        double yGrad = gradient(x, y + 1, x, y - 1); // Square y-gradient.
        double energy = Math.sqrt(xGrad + yGrad);
        return energy;
    }

    private int gradient(int x1, int y1, int x2, int y2) {
        Color color1 = picture.get(x1, y1);
        Color color2 = picture.get(x2, y2);
        // Squared differences in red, green, and blue components.
        int red = color1.getRed() - color2.getRed();
        int green = color1.getGreen() - color2.getGreen();
        int blue = color1.getBlue() - color2.getBlue();
        int grad = square(red) + square(green) + square(blue); // Gradient.
        return grad;
    }

    private int square(int x) {
        return x * x;
    }

    private void validateIndices(int x, int y) {
        StringBuilder error = new StringBuilder();
        if (x < 0 || x >= width) {
            error.append("x must be between 0 and ");
            error.append(width - 1);
            throw new IllegalArgumentException(error.toString());
        } else if (y < 0 || y >= height) {
            error.append("y must be between 0 and ");
            error.append(height - 1);
            throw new IllegalArgumentException(error.toString());
        }
    }

    /**
     * Sequence of indices for horizontal seam.
     */
    public int[] findHorizontalSeam() {
        // TODO: Implement this method.
        return null;
    }

    /**
     * Sequence of indices for vertical seam.
     */
    public int[] findVerticalSeam() {
        // TODO: Implement this method.
        return null;
    }

    /**
     * Remove horizontal seam from current picture.
     */
    public void removeHorizontalSeam(int[] seam) {
        validateArg(seam);
        validateHeight();
        validateHorizontalSeam(seam);
    }

    private void validateHeight() {
        if (height <= 1) {
            String error = "Height must be greater than 1";
            throw new IllegalArgumentException(error);
        }
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
        validateWidth();
        validateVerticalSeam(seam);
    }

    private void validateWidth() {
        if (width <= 1) {
            String error = "Width must be greater than 1";
            throw new IllegalArgumentException(error);
        }
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
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        SeamCarver sc = new SeamCarver(picture);
        StdOut.println("Width: " + sc.width());
        StdOut.println("Height: " + sc.height());
        double energy = sc.energy(x, y);
        StdOut.printf("Energy of pixel at (%d, %d): %f\n", x, y, energy);
    }
}

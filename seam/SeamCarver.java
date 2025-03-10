import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

/**
 * Seam carving.
 * 
 * @author Yaohui Wu
 */
public class SeamCarver {
    private int width;
    private int height;
    private int[][] picture;
    double[][] energys;

    /**
     * Create a seam carver object based on the given picture.
     */
    public SeamCarver(Picture picture) {
        validateArg(picture);
        width = picture.width();
        height = picture.height();
        this.picture = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.picture[i][j] = picture.getRGB(i, j);
            }
        }
        energys = new double[width][height];
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
        Picture pic = new Picture(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = picture[i][j];
                pic.setRGB(i, j, rgb);
            }
        }
        return pic;
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
        // The energy of a pixel at the border of the image is 1,000.0.
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
            return 1_000.0;
        }
        if (energys[x][y] != 0.0) {
            return energys[x][y];
        }
        // Dual-gradient energy function.
        double xGrad = gradient(x + 1, y, x - 1, y); // Square x-gradient.
        double yGrad = gradient(x, y + 1, x, y - 1); // Square y-gradient.
        energys[x][y] = Math.sqrt(xGrad + yGrad);
        return energys[x][y];
    }

    private void validateIndices(int x, int y) {
        StringBuilder error = new StringBuilder();
        if (x < 0 || x >= width) {
            error.append("x must be between 0 and ");
            error.append(width - 1);
            throw new IllegalArgumentException(error.toString());
        }
        if (y < 0 || y >= height) {
            error.append("y must be between 0 and ");
            error.append(height - 1);
            throw new IllegalArgumentException(error.toString());
        }
    }

    private int gradient(int x1, int y1, int x2, int y2) {
        int rgb1 = picture[x1][y1];
        int rgb2 = picture[x2][y2];
        // Squared differences in red, green, and blue components.
        int red = getRed(rgb1) - getRed(rgb2);
        int green = getGreen(rgb1) - getGreen(rgb2);
        int blue = getBlue(rgb1) - getBlue(rgb2);
        int grad = square(red) + square(green) + square(blue); // Gradient.
        return grad;
    }

    private int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    private int getGreen(int rgb) {
        return (rgb >>  8) & 0xFF;
    }

    private int getBlue(int rgb) {
        return (rgb >>  0) & 0xFF;
    }

    private int square(int x) {
        return x * x;
    }

    /**
     * Sequence of indices for vertical seam.
     */
    public int[] findVerticalSeam() {
        int[] seam = new int[height];
        int width = width();
        // Distances to the end of the seam.
        double[][] distTo = new double[height][width];
        // Edges to the end of the seam.
        int[][] edgeTo = new int[height][width];
        for (int j = 1; j < height; j++) {
            Arrays.fill(distTo[j], Double.POSITIVE_INFINITY);
        }
        Arrays.fill(distTo[0], 0.0);
        int x = 0; // End of the seam.
        double min = Double.POSITIVE_INFINITY;
        for (int j = 1; j < height; j++) {
            for (int i = 0; i < width; i++) {
                double energy = energy(i, j);
                for (int k = -1; k < 2; k++) {
                    int m = i + k;
                    if (m >= 0 && m < width) {
                        double d = distTo[j - 1][m] + energy;
                        double dist = distTo[j][i];
                        if (dist > d) {
                            distTo[j][i] = d;
                            edgeTo[j][i] = m;
                        }
                    }
                }
                if (j == height - 1 && distTo[j][i] < min) {
                    min = distTo[j][i];
                    x = i;
                }
            }
        }
        for (int j = height - 1; j >= 0; j--) {
            seam[j] = x;
            x = edgeTo[j][x];
        }
        return seam;
    }

    /**
     * Sequence of indices for horizontal seam.
     */
    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }

    private void transpose() {
        int[][] newPicture = new int[height][width];
        double[][] newEnergys = new double[height][width];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                newPicture[j][i] = picture[i][j];
                newEnergys[j][i] = energys[i][j];
            }
        }
        int temp = width;
        width = height;
        height = temp;
        picture = newPicture;
        energys = newEnergys;
    }

    /**
     * Remove vertical seam from current picture.
     */
    public void removeVerticalSeam(int[] seam) {
        validateArg(seam);
        validateWidth();
        validateVerticalSeam(seam);
        for (int j = 0; j < height; j++) {
            for (int i = seam[j]; i < width - 1; i++) {
                picture[i][j] = picture[i + 1][j];
                energys[i][j] = energys[i + 1][j];
            }
        }
        for (int j = 0; j < height; j++) {
            for (int i = -1; i < 2; i++) {
                int k = seam[j] + i;
                if (k >= 0 && k < width) {
                    energys[k][j] = 0.0;
                }
            }
        }
        picture[width - 1] = null;
        energys[width - 1] = null;
        width -= 1;
    }

    private void validateWidth() {
        int width = width();
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
     * Remove horizontal seam from current picture.
     */
    public void removeHorizontalSeam(int[] seam) {
        validateArg(seam);
        validateHeight();
        validateHorizontalSeam(seam);
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    private void validateHeight() {
        int height = height();
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
        int[] verticalSeam = sc.findVerticalSeam();
        StdOut.println("Vertical seam:");
        StdOut.println(Arrays.toString(verticalSeam));
        int[] horizontalSeam = sc.findHorizontalSeam();
        StdOut.println("Horizontal seam:");
        StdOut.println(Arrays.toString(horizontalSeam));
        sc.removeVerticalSeam(verticalSeam);
        sc.removeHorizontalSeam(horizontalSeam);
        Picture newPicture = sc.picture();
        newPicture.show();
    }
}

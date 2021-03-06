package cw6.util;

import common.ConversionsCommon;
import common.Picture;
import common.ReaderUtil;
import cw4.util.ConversionsCw4;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * User: rafik991@gmail.com
 * Date: 11/24/13
 */
public class ConversionsCw6 {
    public static final String R = "R";
    public static final String G = "G";
    public static final String B = "B";
    private ConversionsCommon conversionsCommon = new ConversionsCommon();
    private ConversionsCw4 conversionsCw4 = new ConversionsCw4();
    private ReaderUtil readerUtil = new ReaderUtil();

    public BufferedImage erosion(Picture picture, String file) throws IOException {
        BufferedImage src = conversionsCw4.otsuBinaryConversion(picture);
        int matrix[][] = readerUtil.getFileMatrix(file);
        int half = matrix.length / 2;
        int matrixSize = matrix.length * matrix[0].length;
        BufferedImage filtered = new BufferedImage(src.getWidth() + 2 * matrix.length, src.getHeight() + 2 * matrix.length,
                src.getType());

        fillFilteredImage(src, matrix, filtered);

        for (int i = 2 * matrix.length; i < filtered.getWidth() - 2 * matrix.length; i++) {
            for (int j = 2 * matrix.length; j < filtered.getHeight() - 2 * matrix.length; j++) {
                if (conversionsCommon.checkPassingErosion(matrix, half, filtered, i, j)) {
                    int newPixel = conversionsCommon.colorToRGB24Bits(ConversionsCommon.RBG_MAX, ConversionsCommon.RBG_MAX, ConversionsCommon.RBG_MAX);
                    src.setRGB(i - 2 * matrix.length, j - 2 * matrix.length, newPixel);
                }
            }
        }
        return src;
    }

    private void fillFilteredImage(BufferedImage src, int[][] matrix, BufferedImage filtered) {
        for (int k = 0; k < filtered.getWidth(); k++) {
            for (int w = 0; w < filtered.getHeight(); w++) {
                if (k >= 2 * matrix.length && w >= 2 * matrix.length) {
                    filtered.setRGB(k, w, src.getRGB(k - matrix.length * 2, w - matrix.length * 2));
                } else {
                    filtered.setRGB(k, w, -1);
                }
            }
        }
    }

    public BufferedImage circleErosion(Picture picture, int r) {
        BufferedImage src = conversionsCw4.otsuBinaryConversion(picture);

        if (r * 2 > src.getWidth() || r * 2 > src.getHeight()) {
            throw new RuntimeException("R is too large");
        }
        int[][] matrix = defineCircleMatrix(r, r + 10, r + 10);
        int half = matrix.length / 2;
        int matrixSize = matrix.length * matrix[0].length;
        BufferedImage filtered = new BufferedImage(src.getWidth() + 2 * matrix.length, src.getHeight() + 2 * matrix.length,
                src.getType());

        fillFilteredImage(src, matrix, filtered);
        for (int i = 2 * matrix.length; i < filtered.getWidth() - 2 * matrix.length; i++) {
            for (int j = 2 * matrix.length; j < filtered.getHeight() - 2 * matrix.length; j++) {
                if (conversionsCommon.checkPassingErosion(matrix, half, filtered, i, j)) {
                    int newPixel = conversionsCommon.colorToRGB24Bits(ConversionsCommon.RBG_MAX, ConversionsCommon.RBG_MAX, ConversionsCommon.RBG_MAX);
                    src.setRGB(i - 2 * matrix.length, j - 2 * matrix.length, newPixel);
                }
            }
        }
        return src;

    }

    public BufferedImage dilatation(Picture picture, String file) throws IOException {
        BufferedImage src = conversionsCw4.otsuBinaryConversion(picture);
        int matrix[][] = readerUtil.getFileMatrix(file);
        int half = matrix.length / 2;
        int matrixSize = matrix.length * matrix[0].length;
        BufferedImage filtered = new BufferedImage(src.getWidth() + 2 * matrix.length, src.getHeight() + 2 * matrix.length,
                src.getType());
        fillFilteredImage(src, matrix, filtered);
        for (int i = 2 * matrix.length; i < filtered.getWidth() - 2 * matrix.length; i++) {
            for (int j = 2 * matrix.length; j < filtered.getHeight() - 2 * matrix.length; j++) {
                if (conversionsCommon.checkPassing(matrix, half, filtered, i, j)) {
                    src.setRGB(i - 2 * matrix.length, j - 2 * matrix.length, 0);
                }
            }
        }

        return src;
    }

    public BufferedImage erosionForRGB(Picture picture, String file, String rgb) throws IOException {
        BufferedImage src = conversionsCw4.otsuBinaryConversion(picture);
        int matrix[][] = readerUtil.getFileMatrix(file);
        int half = matrix.length / 2;
        int matrixSize = matrix.length * matrix[0].length;
        BufferedImage filtered = new BufferedImage(src.getWidth() + 2 * matrix.length, src.getHeight() + 2 * matrix.length,
                src.getType());
        fillFilteredImage(src, matrix, filtered);
        int red, green, blue;
        for (int i = 2 * matrix.length; i < filtered.getWidth() - 2 * matrix.length; i++) {
            for (int j = 2 * matrix.length; j < filtered.getHeight() - 2 * matrix.length; j++) {

                red = new Color(filtered.getRGB(i, j)).getRed();
                green = new Color(filtered.getRGB(i, j)).getGreen();
                blue = new Color(filtered.getRGB(i, j)).getBlue();
                if (conversionsCommon.checkPassing(matrix, half, filtered, i, j)) {
                    switch (rgb) {
                        case R:
                            red = 0;
                            break;
                        case G:
                            green = 0;
                            break;
                        case B:
                            blue = 0;
                            break;
                    }

                    src.setRGB(i - 2 * matrix.length, j - 2 * matrix.length, conversionsCommon.colorToRGB24Bits(red, green, blue));
                }
            }
        }
        return src;
    }

    public BufferedImage dilatationForRGB(Picture picture, String file, String rgb) throws IOException {
        BufferedImage src = conversionsCw4.otsuBinaryConversion(picture);
        int matrix[][] = readerUtil.getFileMatrix(file);
        int half = matrix.length / 2;
        int matrixSize = matrix.length * matrix[0].length;
        BufferedImage filtered = new BufferedImage(src.getWidth() + 2 * matrix.length, src.getHeight() + 2 * matrix.length,
                src.getType());
        fillFilteredImage(src, matrix, filtered);
        int red, green, blue;
        for (int i = 2 * matrix.length; i < filtered.getWidth() - 2 * matrix.length; i++) {
            for (int j = 2 * matrix.length; j < filtered.getHeight() - 2 * matrix.length; j++) {
                red = new Color(filtered.getRGB(i, j)).getRed();
                green = new Color(filtered.getRGB(i, j)).getGreen();
                blue = new Color(filtered.getRGB(i, j)).getBlue();

                if (conversionsCommon.checkPassing(matrix, half, filtered, i, j)) {
                    switch (rgb) {
                        case R:
                            red = 1;
                            break;
                        case G:
                            green = 1;
                            break;
                        case B:
                            blue = 1;
                            break;
                    }
                    src.setRGB(i - 2 * matrix.length, j - 2 * matrix.length, conversionsCommon.colorToRGB24Bits(red, green, blue));
                }
            }
        }
        return src;
    }

    private int[][] defineCircleMatrix(int r, int w, int h) {
        int[][] matrix = new int[w][h];
        int half = w / 2;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if ((half - i) * (half - i) + (half - j) * (half - j) < r * r) {
                    matrix[i][j] = 1;
                }
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        return matrix;
    }
}

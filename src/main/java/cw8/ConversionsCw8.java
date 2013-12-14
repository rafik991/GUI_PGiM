package cw8;

import common.ConversionsCommon;
import common.Picture;
import cw4.ConversionsCw4;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * rafik991@gmai.com
 * 12/8/13
 */
public class ConversionsCw8 {
    public static final String R = "R";
    public static final String G = "G";
    public static final String B = "B";
    final static int nSalt = 5;    // Percentage of salt
    final static int nPepper = 5;
    private ConversionsCommon conversionsCommon = new ConversionsCommon();
    private ConversionsCw4 conversionsCw4 = new ConversionsCw4();

    public BufferedImage steadyDysfunction(Picture picture, int lvl, double prob) {
        BufferedImage src = picture.getImage();
        BufferedImage filtered = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        int red, green, blue, newPixel;
        int probability = (int) (src.getWidth() * src.getHeight() * prob) & 20;
        for (int i = 0; i < filtered.getWidth(); i++) {
            for (int j = 0; j < filtered.getHeight(); j++) {
                int randomLvl = -lvl + (int) (Math.random() * ((lvl - (-lvl)) + 1));

                red = new Color(src.getRGB(i, j)).getRed();
                green = new Color(src.getRGB(i, j)).getGreen();
                blue = new Color(src.getRGB(i, j)).getBlue();
                if ((i * filtered.getWidth() + j % probability) == 0) {
                    red += randomLvl;
                    green += randomLvl;
                    blue += randomLvl;
                }
                newPixel = conversionsCommon.colorToRGB24Bits(red, green, blue);
                filtered.setRGB(i, j, newPixel);
            }
        }
        return filtered;
    }

    public BufferedImage normalDysfunction(Picture picture, int mean, int variation, double prob) {
        BufferedImage src = picture.getImage();
        BufferedImage filtered = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        int red, green, blue, newPixel;
        int probability = (int) (src.getWidth() * src.getHeight() * prob) % 100;
        for (int i = 0; i < filtered.getWidth(); i++) {
            for (int j = 0; j < filtered.getHeight(); j++) {
                int randomLvl = -mean + (int) (Math.random() * ((mean - (-mean)) + 1));
                red = new Color(src.getRGB(i, j)).getRed();
                green = new Color(src.getRGB(i, j)).getGreen();
                blue = new Color(src.getRGB(i, j)).getBlue();
                if ((i * filtered.getWidth() + j) % probability == 0) {
                    red += randomLvl;
                    green += randomLvl;
                    blue += randomLvl;
                }
                newPixel = conversionsCommon.colorToRGB24Bits(red, green, blue);
                filtered.setRGB(i, j, newPixel);
            }
        }
        return filtered;
    }

    public BufferedImage saltAndPepperNoise(Picture picture, double prob) {
        BufferedImage src = picture.getImage();
        BufferedImage filtered = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        int red, green, blue, newPixel;
        int probability = (int) (src.getWidth() * src.getHeight() * prob);
        for (int i = 0; i < filtered.getWidth(); i++) {
            for (int j = 0; j < filtered.getHeight(); j++) {
                int randomLvl = 0 + (int) (Math.random() * ((255 - (0)) + 1));

                red = new Color(src.getRGB(i, j)).getRed();
                green = new Color(src.getRGB(i, j)).getGreen();
                blue = new Color(src.getRGB(i, j)).getBlue();
                newPixel = conversionsCommon.colorToRGB24Bits(red, green, blue);
                filtered.setRGB(i, j, newPixel);
            }
        }


        int height = src.getHeight();
        int width = src.getWidth();

        int salt = height * width * nSalt / 100;    // Amount of salt
        int pepper = height * width * nSalt / 100;  // Amount of pepper

        for (int i = 0; i < salt; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);

            filtered.setRGB(x, y, conversionsCommon.colorToRGB24Bits(ConversionsCommon.RBG_MAX,
                    ConversionsCommon.RBG_MAX, ConversionsCommon.RBG_MAX));
        }

        for (int i = 0; i < pepper; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);

            filtered.setRGB(x, y, conversionsCommon.colorToRGB24Bits(0,
                    0, 0));
        }
        return filtered;
    }

    public BufferedImage meanFilter(Picture picture, int windowSize, String rgb) {
        BufferedImage src = conversionsCw4.otsuBinaryConversion(picture);
        BufferedImage filtered = new BufferedImage(src.getWidth() + 2 * windowSize,
                src.getHeight() + 2 * windowSize,
                src.getType());
        fillFilteredImage(src, windowSize, filtered);
        int red, green, blue, newPixel;
        for (int i = 2 * windowSize; i < filtered.getWidth() - 2 * windowSize; i++) {
            for (int j = 2 * windowSize; j < filtered.getHeight() - 2 * windowSize; j++) {
                red = new Color(filtered.getRGB(i, j)).getRed();
                green = new Color(filtered.getRGB(i, j)).getGreen();
                blue = new Color(filtered.getRGB(i, j)).getBlue();
                if (!rgb.isEmpty()) {
                    switch (rgb) {
                        case R:
                            red = computeMean(windowSize, windowSize / 2, filtered, i, j, R);
                            break;
                        case G:
                            green = computeMean(windowSize, windowSize / 2, filtered, i, j, G);
                            break;
                        case B:
                            blue = computeMean(windowSize, windowSize / 2, filtered, i, j, B);
                            break;
                    }
                } else {
                    red = computeMean(windowSize, windowSize / 2, filtered, i, j, R);
                    green = computeMean(windowSize, windowSize / 2, filtered, i, j, G);
                    blue = computeMean(windowSize, windowSize / 2, filtered, i, j, B);
                }
                newPixel = conversionsCommon.colorToRGB24Bits(red, green, blue);
                src.setRGB(i - 2 * windowSize, j - 2 * windowSize, newPixel);

            }
        }
        return src;
    }

    public BufferedImage medianFilter(Picture picture, int windowSize, String rgb) {
        BufferedImage src = conversionsCw4.otsuBinaryConversion(picture);
        BufferedImage filtered = new BufferedImage(src.getWidth() + 2 * windowSize,
                src.getHeight() + 2 * windowSize,
                src.getType());
        fillFilteredImage(src, windowSize, filtered);
        int red, green, blue, newPixel;
        for (int i = 2 * windowSize; i < filtered.getWidth() - 2 * windowSize; i++) {
            for (int j = 2 * windowSize; j < filtered.getHeight() - 2 * windowSize; j++) {
                red = new Color(filtered.getRGB(i, j)).getRed();
                green = new Color(filtered.getRGB(i, j)).getGreen();
                blue = new Color(filtered.getRGB(i, j)).getBlue();
                if (!rgb.isEmpty()) {
                    switch (rgb) {
                        case R:
                            red = computeMedian(windowSize, windowSize / 2, filtered, i, j, R);
                            break;
                        case G:
                            green = computeMedian(windowSize, windowSize / 2, filtered, i, j, G);
                            break;
                        case B:
                            blue = computeMedian(windowSize, windowSize / 2, filtered, i, j, B);
                            break;
                    }
                } else {
                    red = computeMedian(windowSize, windowSize / 2, filtered, i, j, R);
                    green = computeMedian(windowSize, windowSize / 2, filtered, i, j, G);
                    blue = computeMedian(windowSize, windowSize / 2, filtered, i, j, B);
                }
                newPixel = conversionsCommon.colorToRGB24Bits(red, green, blue);
                src.setRGB(i - 2 * windowSize, j - 2 * windowSize, newPixel);

            }
        }
        return src;
    }

    private void fillFilteredImage(BufferedImage src, int windowSize, BufferedImage filtered) {
        for (int k = 0; k < filtered.getWidth(); k++) {
            for (int w = 0; w < filtered.getHeight(); w++) {
                if (k >= 2 * windowSize && w >= 2 * windowSize) {
                    filtered.setRGB(k, w, src.getRGB(k - windowSize * 2, w - windowSize * 2));
                } else {
                    filtered.setRGB(k, w, -1);
                }
            }
        }
    }

    private int computeMean(int windowSize, int half, BufferedImage filtered, int i, int j, String rgb) {
        int count = 0;
        int red, green, blue;
        for (int s = 0; s < windowSize; s++) {
            for (int c = 0; c < windowSize; c++) {
                if (filtered.getRGB(i - half + s, j - half + c) == windowSize) {
                    red = new Color(filtered.getRGB(i, j)).getRed();
                    green = new Color(filtered.getRGB(i, j)).getGreen();
                    blue = new Color(filtered.getRGB(i, j)).getBlue();
                    switch (rgb) {
                        case R:
                            count += red;
                            break;
                        case G:
                            count += green;
                            break;
                        case B:
                            count += blue;
                            break;
                    }
                }
            }
        }
        return count / windowSize * windowSize;
    }

    private int computeMedian(int windowSize, int half, BufferedImage filtered, int i, int j, String rgb) {
        int median = 0;
        int red, green, blue;
        List<Integer> array = new ArrayList<>();
        for (int s = 0; s < windowSize; s++) {
            for (int c = 0; c < windowSize; c++) {
                if (filtered.getRGB(i - half + s, j - half + c) == windowSize) {
                    red = new Color(filtered.getRGB(i, j)).getRed();
                    green = new Color(filtered.getRGB(i, j)).getGreen();
                    blue = new Color(filtered.getRGB(i, j)).getBlue();
                    switch (rgb) {
                        case R:
                            array.add(red);
                            break;
                        case G:
                            array.add(green);
                            break;
                        case B:
                            array.add(blue);
                            break;
                    }
                }
            }
        }
        Collections.sort(array);

        if (array.size() % 2 == 0) {
            median = (array.get(array.size() - 1) + array.get(array.size())) / 2;
        } else {
            median = array.get(array.size() / 2);
        }
        return median;
    }
}

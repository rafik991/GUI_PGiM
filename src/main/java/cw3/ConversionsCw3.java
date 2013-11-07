package cw3;

import common.ConversionsCommon;
import common.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * User: SG0219139
 * Date: 11/4/13
 */
public class ConversionsCw3 {
    private final String R_MIN = "RMIN";
    private final String R_MAX = "RMAX";
    private final String G_MIN = "GMIN";
    private final String G_MAX = "GMAX";
    private final String B_MIN = "BMIN";
    private final String B_MAX = "BMAX";
    private ConversionsCommon conversionsCommon = new ConversionsCommon();

    public Map<Integer, Integer> createHistogram(Picture p) {
        Map<Integer, Integer> histogram = new HashMap<Integer, Integer>();

        for (int i = 0; i < p.getImage().getWidth(); i++) {
            for (int j = 0; j < p.getImage().getHeight(); j++) {
                if (!histogram.containsKey(p.getImage().getRGB(i, j))) {
                    histogram.put(p.getImage().getRGB(i, j), 1);
                } else {
                    int nVar = histogram.get(p.getImage().getRGB(i, j));
                    nVar++;
                    histogram.put(p.getImage().getRGB(i, j), nVar);
                }
            }
        }
        return histogram;
    }

    private int[] getContrastLutTable(double a) {
        int[] LUT = new int[ConversionsCommon.RBG_MAX];
        if (a > 0)
            a += 0.05;
        else if (a < 0)
            a -= 0.05;

        if (a == 0.00)
            a = 0.00;
        for (int i = 0; i < ConversionsCommon.RBG_MAX; i++) {
            if ((a * (i - ConversionsCommon.RBG_MAX / 2) + ConversionsCommon.RBG_MAX / 2) > ConversionsCommon.RBG_MAX)
                LUT[i] = ConversionsCommon.RBG_MAX;
            else if ((a * (i - ConversionsCommon.RBG_MAX / 2) + ConversionsCommon.RBG_MAX / 2) < 0)
                LUT[i] = 0;
            else
                LUT[i] = ((int) (a * (i - ConversionsCommon.RBG_MAX / 2) + ConversionsCommon.RBG_MAX / 2)) % ConversionsCommon.RBG_MAX;
        }
        return LUT;
    }

    public BufferedImage contrast(Picture p, double a) {
        BufferedImage src = p.getImage();
        BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        int red, green, blue;
        int LUT[] = getContrastLutTable(a);
        for (int i = 0; i < src.getWidth(); i++) {
            for (int j = 0; j < src.getHeight(); j++) {
                red = new Color(src.getRGB(i, j)).getRed();
                green = new Color(src.getRGB(i, j)).getGreen();
                blue = new Color(src.getRGB(i, j)).getBlue();
                int newPixel = conversionsCommon.colorToRGB(LUT[red % ConversionsCommon.RBG_MAX], LUT[green % ConversionsCommon.RBG_MAX], LUT[blue % ConversionsCommon.RBG_MAX]);
                result.setRGB(i, j, newPixel);

            }
        }
        return result;
    }

    private Map<String, Integer> getMinMax(Picture pic) {

        int rmin, gmin, bmin, rmax, gmax, bmax;
        int rvalue, gvalue, bvalue;
        rmin = gmin = bmin = ConversionsCommon.RBG_MAX;
        rmax = gmax = bmax = 1;
        Map<String, Integer> res = new HashMap<String, Integer>();

        for (int i = 0; i < pic.getImage().getWidth(); i++) {
            for (int j = 0; j < pic.getImage().getHeight(); j++) {
                rvalue = new Color(pic.getImage().getRGB(i, j)).getRed();
                gvalue = new Color(pic.getImage().getRGB(i, j)).getGreen();
                bvalue = new Color(pic.getImage().getRGB(i, j)).getBlue();
                if (rvalue > rmax) rmax = rvalue;
                if (gvalue > gmax) gmax = gvalue;
                if (bvalue > bmax) bmax = bvalue;
                if (rvalue < rmin) rmin = rvalue;
                if (gvalue < gmin) gmin = gvalue;
                if (bvalue < bmin) bmin = bvalue;
            }
        }
        res.put(R_MAX, rmax);
        res.put(R_MIN, rmin);
        res.put(G_MAX, gmax);
        res.put(G_MIN, gmin);
        res.put(B_MAX, bmax);
        res.put(B_MIN, bmin);
        return res;
    }

    private int[] updateElongateLUT(int[] LUT, double a, int b) {
        int i;
        for (i = 0; i < ConversionsCommon.RBG_MAX; i++)
            if ((a * (i + b)) > ConversionsCommon.RBG_MAX)
                LUT[i] = ConversionsCommon.RBG_MAX;
            else if ((a * (i + b)) < 0)
                LUT[i] = 0;
            else
                LUT[i] = (int) (a * (i + b));

        return LUT;
    }

    public BufferedImage elongateHistoRGB(Picture pic) {
        int red, green, blue;
        int LUTr[] = new int[ConversionsCommon.RBG_MAX];
        int LUTg[] = new int[ConversionsCommon.RBG_MAX];
        int LUTb[] = new int[ConversionsCommon.RBG_MAX];
        BufferedImage src = pic.getImage();
        BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        Map<String, Integer> minMax = getMinMax(pic);
        updateElongateLUT(LUTr, ConversionsCommon.RBG_MAX / (minMax.get(R_MAX) - minMax.get(R_MIN)), -minMax.get(R_MIN));
        updateElongateLUT(LUTg, ConversionsCommon.RBG_MAX / (minMax.get(G_MAX) - minMax.get(G_MIN)), -minMax.get(R_MIN));
        updateElongateLUT(LUTb, ConversionsCommon.RBG_MAX / (minMax.get(B_MAX) - minMax.get(G_MIN)), -minMax.get(R_MIN));

        for (int i = 0; i < src.getWidth(); i++) {
            for (int j = 0; j < src.getHeight(); j++) {
                red = new Color(src.getRGB(i, j)).getRed();
                green = new Color(src.getRGB(i, j)).getGreen();
                blue = new Color(src.getRGB(i, j)).getBlue();
                int newPixel = conversionsCommon.colorToRGB(LUTr[red % ConversionsCommon.RBG_MAX], LUTg[green % ConversionsCommon.RBG_MAX], LUTb[blue % ConversionsCommon.RBG_MAX]);
                result.setRGB(i, j, newPixel);
            }
        }
        return result;
    }

    public BufferedImage elongateHistoY(Picture pic) {
        int red, green, blue;
        int LUTgray[] = new int[ConversionsCommon.RBG_MAX];

        BufferedImage src = pic.getImage();
        BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());

        int grayMin = ConversionsCommon.RBG_MAX;
        int grayMax = 1;
        int y,cb,cr;
        for (int i = 0; i < src.getWidth(); i++) {
            for (int j = 0; j < src.getHeight(); j++) {
                red = new Color(src.getRGB(i, j)).getRed();
                green = new Color(src.getRGB(i, j)).getGreen();
                blue = new Color(src.getRGB(i, j)).getBlue();
                y = (int) (ConversionsCommon.KR * red + (1 - ConversionsCommon.KR - ConversionsCommon.KB) * green + ConversionsCommon.KB * blue);
                if (grayMin > y) grayMin = y;
                if (grayMax < y) grayMax = y;
            }
        }
        updateElongateLUT(LUTgray,ConversionsCommon.RBG_MAX / (grayMax - grayMin), -grayMin);
        for (int i = 0; i < src.getWidth(); i++) {
            for (int j = 0; j < src.getHeight(); j++) {
                red = new Color(src.getRGB(i, j)).getRed();
                green = new Color(src.getRGB(i, j)).getGreen();
                blue = new Color(src.getRGB(i, j)).getBlue();
                y = (int) (ConversionsCommon.KR * red + (1 - ConversionsCommon.KR - ConversionsCommon.KB) * green + ConversionsCommon.KB * blue);
                cb = (int) ((0.5) * ((blue - y) / (1 - ConversionsCommon.KB)));
                cr = (int) ((0.5) * ((red - y) / (1 - ConversionsCommon.KR)));
                int newPixel = conversionsCommon.colorToRGB(LUTgray[y % ConversionsCommon.RBG_MAX],LUTgray[y % ConversionsCommon.RBG_MAX],LUTgray[y % ConversionsCommon.RBG_MAX]);
                result.setRGB(i, j, newPixel);
            }
        }
        return result;
    }
}
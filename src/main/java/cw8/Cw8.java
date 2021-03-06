package cw8;

import common.ConversionsCommon;
import common.Picture;
import common.PictureCustoms;
import cw4.util.ConversionsCw4;
import cw8.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * rafik991@gmai.com
 * 12/8/13
 */
public class Cw8 {
    public Cw8(Picture picture) {
        cw8(picture);
    }

    private void cw8(final Picture picture) {
        final ConversionsCw8 conversionsCw8 = new ConversionsCw8();
        final ConversionsCw4 conversionsCw4 = new ConversionsCw4();
        final ConversionsCommon conversionsCommon = new ConversionsCommon();
        JMenuBar bar = picture.getFrame().getJMenuBar();
        final Image image = picture.getImage();
        JMenu cw8 = new JMenu("CW8");
        bar.add(cw8);

        JMenuItem zad1 = new JMenuItem("zad1");
        zad1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SteadyDysfunctionArgDialog steadyDysfunctionArgDialog = new SteadyDysfunctionArgDialog(picture,
                        false, "Pass level,probability");
                steadyDysfunctionArgDialog.repaint();
            }
        });

        JMenuItem zad1b = new JMenuItem("zad1b");
        zad1b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SteadyDysfunctionArgDialog steadyDysfunctionArgDialog = new SteadyDysfunctionArgDialog
                        (new Picture(conversionsCw4
                                .otsuBinaryConversion(picture)),
                                false, "Pass level,probability");
                steadyDysfunctionArgDialog.repaint();
            }
        });

        JMenuItem zad2 = new JMenuItem("zad2");
        zad2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NormalFilterArgDialog normalFilterArgDialog = new NormalFilterArgDialog(picture, false, "Pass mean," +
                        "variation,probability");
                normalFilterArgDialog.repaint();
            }
        });

        JMenuItem zad2b = new JMenuItem("zad2b");
        zad2b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NormalFilterArgDialog normalFilterArgDialog = new NormalFilterArgDialog(new Picture(conversionsCw4
                        .otsuBinaryConversion(picture)), false,
                        "Pass mean," +
                                "variation,probability");
                normalFilterArgDialog.repaint();
            }
        });

        JMenuItem zad3 = new JMenuItem("zad3");
        zad3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaltPepperArgDialog saltPepperArgDialog = new SaltPepperArgDialog(picture, false, "Pass probability ");
                saltPepperArgDialog.repaint();
            }
        });

        JMenuItem zad3b = new JMenuItem("zad3b");
        zad3b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaltPepperArgDialog saltPepperArgDialog = new SaltPepperArgDialog(new Picture(conversionsCw4
                        .otsuBinaryConversion(picture)),
                        false,
                        "Pass probability ");
                saltPepperArgDialog.repaint();
            }
        });

        JMenuItem zad4 = new JMenuItem("zad4");
        zad4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MovingMeanArgDialog movingMeanArgDialog = new MovingMeanArgDialog(picture, false, "Pass " +
                        "R|G|B and window size");

                movingMeanArgDialog.repaint();

            }
        });

        JMenuItem zad5 = new JMenuItem("zad5");
        zad5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedianArgDialog medianArgDialog = new MedianArgDialog(picture, false, "Pass " +
                        "R|G|B and window size");
                medianArgDialog.repaint();

            }
        });

        JMenuItem zad7 = new JMenuItem("zad7a");
        zad7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Zad7aMovingMeanArgDialog movingMeanArgDialog = new Zad7aMovingMeanArgDialog(picture, false, "Pass window size");
                movingMeanArgDialog.repaint();
            }
        });

        JMenuItem zad7b = new JMenuItem("zad7b");
        zad7b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Zad7bMedianArgDialog medianArgDialog = new Zad7bMedianArgDialog(picture, false, "Pass window size");
                medianArgDialog.repaint();
            }

        });

        JMenuItem gauss = new JMenuItem("gauss");
        gauss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PictureCustoms.showImageInNewWindow(conversionsCw8.gaussianFilter(picture, 20));
            }
        });


        cw8.add(zad1);
        cw8.add(zad1b);
        cw8.add(zad2);
        cw8.add(zad2b);
        cw8.add(zad3);
        cw8.add(zad3b);
        cw8.add(zad4);
        cw8.add(zad5);
        cw8.add(zad7);
        cw8.add(zad7b);
        cw8.add(gauss);
    }
}

package cw2.util;

import common.CommonArgDialog;
import common.Picture;
import common.PictureCustoms;

import java.awt.event.ActionEvent;

/**
 * User: SG0219139
 * Date: 10/21/13
 */
public class PassMovePhotoArg extends CommonArgDialog {
    public PassMovePhotoArg(Picture picture, boolean modal, String myMessage) {
        super(picture, modal, myMessage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (getYesButton() == e.getSource()) {
            Integer arg = Integer.parseInt(getTextField().getText());
            ConversionsCw2 conversionsCw2 = new ConversionsCw2();

            PictureCustoms.showImageInNewWindow(conversionsCw2.movePicture(getPicture(), arg));

        } else if (getNoButton() == e.getSource()) {
            System.err.println("User chose no.");
            setVisible(false);
        }
    }
}

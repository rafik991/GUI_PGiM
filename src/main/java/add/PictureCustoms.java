package add;

import cw1.Cw1;


import javax.swing.*;
import java.awt.*;

/**
 * Created by SG0219139 on 10/13/13.
 */
public class PictureCustoms {
   public static void customMenu(final Picture picture){
       Cw1 cw1 = new Cw1(picture);
   }
    public static void showImageInNewWindow(Image fildered){

        JFrame frame1 = new JFrame();
        frame1.setContentPane(getJLabel(fildered));
        // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setResizable(false);
        frame1.pack();
        frame1.setVisible(true);

        frame1.repaint();
    }
    public static JLabel getJLabel(Image customImage) {
        ImageIcon icon = null;
        if (customImage != null) {
            icon = new ImageIcon(customImage);
        }
        return new JLabel(icon);
    }


}

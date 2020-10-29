package com.bensler.woopu;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertYellow;

/**
 * WooPu application main class bootstrapping everything.
 */
public class App {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, IOException {
      LookUtils.setLookAndTheme(new PlasticLookAndFeel(), new DesertYellow());

      final ImageSource imgSrc = new ImageSource();
      new ApplicationFrame("WooPu", imgSrc.windowIcon.getImage(), new FieldComponent(3, imgSrc));
    }

}

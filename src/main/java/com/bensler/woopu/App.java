package com.bensler.woopu;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertYellow;

/**
 * WooPu application main class bootstrapping everything.
 */
public class App {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, IOException {
      LookUtils.setLookAndTheme(new PlasticLookAndFeel(), new DesertYellow());

      final Image scaledBackground = ImageSource.backgroundImg.getScaledInstance(300);
      final JLabel label = new JLabel(new ImageIcon(scaledBackground));
      label.setBorder(new LineBorder(Color.red, 1));

      new ApplicationFrame("WooPu", new FieldComponent(scaledBackground));
    }

}

package com.bensler.woopu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Basic application frame providing base functionality apart from
 * an actual applications purpose:
 * <ul>
 *   <li>dispose on close (which lets the AWT event dispatch thread die)</li>
 *   <li>ensure a window size not smaller than preferred size</li>
 * </ul>
 */
public class ApplicationFrame extends JFrame {

  public ApplicationFrame(String title, Image iconImg, Component frameContent) {
    super(title);
    setIconImage(iconImg);
    getContentPane().add(frameContent, BorderLayout.CENTER);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setVisible(true);
    pack();
    centerOnScreen();
    limitSize();
  }

  /**
   * Jump back to preferred size when resized below that size.
   */
  private void limitSize() {
    final Dimension prefSize = getPreferredSize();

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
         final Dimension size = getSize();

         if ((prefSize.width > size.width) || (prefSize.height > size.height)) {
           SwingUtilities.invokeLater(() -> {
             setSize(new Dimension(Math.max(size.width, prefSize.width), Math.max(size.height, prefSize.height)));
           });
         }
      }

    });
  }

  public void centerOnScreen() {
    final Rectangle screenBounds = getGraphicsConfiguration().getBounds();
    final Rectangle windowBounds = getBounds();

    final int xPos = ((screenBounds.width - windowBounds.width) / 2);
    final int yPos = ((screenBounds.height - windowBounds.height) / 2);

    setLocation(Math.max(0, xPos), Math.max(0, yPos));
    setSize(
      Math.min(screenBounds.width, windowBounds.width),
      Math.min(screenBounds.height, windowBounds.height)
    );
  }

}

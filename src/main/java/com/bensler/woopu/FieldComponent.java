package com.bensler.woopu;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.bensler.woopu.model.Field;

/**
 * {@link JComponent} displaying a {@link Field} on screen.
 */
public class FieldComponent extends JComponent {

  private final ImageSource imgSrc;
  private final int frameWidth;
  private final ImageIcon backgroundImg;
  private final ImageIcon bluePiece;

  public FieldComponent(int gridScaleFactor, ImageSource anImgSrc) {
    imgSrc = anImgSrc;
    frameWidth = ImageSource.FRAME_SIZE * gridScaleFactor;
    backgroundImg = new ImageIcon(imgSrc.getBackgroundImage(gridScaleFactor));
    final Dimension size = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());

    setMinimumSize(size);
    setPreferredSize(size);
    setMaximumSize(size);

    bluePiece = new ImageIcon(imgSrc.pieceBlueImg.getScaledInstance(130));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    backgroundImg.paintIcon(this, g, 0, 0);

    bluePiece.paintIcon(this, g, 55, 55);
  }

}

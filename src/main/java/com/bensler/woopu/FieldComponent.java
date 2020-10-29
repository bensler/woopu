package com.bensler.woopu;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.Piece;

/**
 * {@link JComponent} displaying a {@link Field} on screen.
 */
public class FieldComponent extends JComponent {

  private final Field field;
  private final FieldImageSource imgSrc;
  private final int frameWidth;
  private final int gridWidth;
  private final ImageIcon backgroundImg;

  public FieldComponent(int gridScaleFactor, ImageSource anImgSrc, Field aField) {
    field = aField;
    imgSrc = anImgSrc.getFieldImageSource(gridScaleFactor);
    frameWidth = ImageSource.FRAME_SIZE * gridScaleFactor;
    gridWidth = ImageSource.GRID_SIZE * gridScaleFactor;
    backgroundImg = new ImageIcon(imgSrc.getBackgroundImage());
    final Dimension size = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());

    setMinimumSize(size);
    setPreferredSize(size);
    setMaximumSize(size);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    backgroundImg.paintIcon(this, g, 0, 0);
    for (Piece piece : field) {
      imgSrc.getPieceImageIcon(piece.type).paintIcon(
        this, g,
        frameWidth + (gridWidth * piece.x),
        frameWidth + (gridWidth * piece.y)
      );
    }
//    new ImageIcon(imgSrc.getPieceImage(PieceType.RED_HORIZONTAL)).paintIcon(this, g, frameWidth, frameWidth + (2 * gridWidth));
  }

}

package com.bensler.woopu;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.PieceType;

/**
 * {@link JComponent} displaying a {@link Field} on screen.
 */
public class FieldComponent extends JComponent {

  protected final FieldImageSource imgSrc;
  protected final int frameWidth;
  protected final int gridWidth;
  protected final ImageIcon backgroundImg;

  protected Field field;

  public FieldComponent(float gridScaleFactor, ImageSource anImgSrc, Field aField) {
    field = aField;
    imgSrc = anImgSrc.getFieldImageSource(gridScaleFactor);
    frameWidth = Math.round(ImageSource.FRAME_SIZE * gridScaleFactor);
    gridWidth = Math.round(ImageSource.GRID_SIZE * gridScaleFactor);
    backgroundImg = new ImageIcon(imgSrc.getBackgroundImage());
    final Dimension size = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());

    setMinimumSize(size);
    setPreferredSize(size);
    setMaximumSize(size);
  }

  public void setField(Field newField) {
    field = newField;
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    paintBackground(g);
    field.pieces().forEach(piece -> paintPiece(g,
      piece.type,
      frameWidth + (gridWidth * piece.getLeftX()),
      frameWidth + (gridWidth * piece.getTopY())
    ));
  }

  protected void paintBackground(Graphics g) {
    backgroundImg.paintIcon(this, g, 0, 0);
  }

  protected void paintPiece(Graphics g, PieceType type, int x, int y) {
    imgSrc.getPieceImageIcon(type).paintIcon(
      this, g, x, y
    );
  }

}

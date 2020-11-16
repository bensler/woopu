package com.bensler.woopu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.Piece;

/**
 * {@link JComponent} displaying a {@link Field} on screen.
 */
public class FieldComponent extends JComponent {

  protected final FieldImageSource imgSrc;
  protected final int frameWidth;
  protected final int gridSize;
  protected final ImageIcon backgroundImg;

  protected Field field;

  public FieldComponent(float gridScaleFactor, ImageSource anImgSrc, Field aField) {
    field = aField;
    imgSrc = anImgSrc.getFieldImageSource(gridScaleFactor);
    frameWidth = Math.round(ImageSource.FRAME_SIZE * gridScaleFactor);
    gridSize = Math.round(ImageSource.GRID_SIZE * gridScaleFactor);
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
    field.pieces().forEach(piece -> paintPiece(g, piece, 0, 0));
  }

  protected void paintBackground(Graphics g) {
    backgroundImg.paintIcon(this, g, 0, 0);
  }

  protected Rectangle paintPiece(Graphics g, Piece piece, int deltaXPix, int deltaYPix) {
    final Rectangle piecePixBounds = getPiecePixBounds(piece);

    piecePixBounds.x += deltaXPix;
    piecePixBounds.y += deltaYPix;
    imgSrc.getPieceImageIcon(piece.type).paintIcon(
      this, g, piecePixBounds.x, piecePixBounds.y
    );
    return piecePixBounds;
  }

  protected Rectangle getPiecePixBounds(Piece piece) {
    return getGridPixBounds(piece.getLeftX(), piece.getTopY(), piece.getWidth(), piece.getHeight());
  }

  protected Rectangle getGridPixBounds(int gridX, int gridY, int gridWidth, int gridHeight) {
    return new Rectangle(
      frameWidth + (gridSize * gridX),
      frameWidth + (gridSize * gridY),
      gridSize * gridWidth,
      gridSize * gridHeight
    );
  }

}

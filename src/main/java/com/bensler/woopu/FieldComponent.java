package com.bensler.woopu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class FieldComponent extends JComponent {

  private final ImageIcon backgroundImg;
  private final ImageIcon bluePiece;


  public FieldComponent(Image aBackgroundImg) {
    backgroundImg = new ImageIcon(aBackgroundImg);
    final Dimension size = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());

    setMinimumSize(size);
    setPreferredSize(size);
    setMaximumSize(size);

    bluePiece = new ImageIcon(Piece.pieceBlue.getScaledInstance(130));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    backgroundImg.paintIcon(this, g, 0, 0);

    bluePiece.paintIcon(this, g, 55, 55);
  }

}

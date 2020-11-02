package com.bensler.woopu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.Piece;

public class InteractiveFieldComponent extends FieldComponent {

  private Piece selectedPiece;
  private Piece selectionCandidate;

  public InteractiveFieldComponent(float gridScaleFactor, ImageSource anImgSrc, Field aField) {
    super(gridScaleFactor, anImgSrc, aField);

    final MouseAdapter leAdapteurDeMouse = new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent evt) {
        mouseOver(evt.getPoint());
      }
      @Override
      public void mouseClicked(MouseEvent evt) {
        selectCandidate();
      }
    };

    addMouseMotionListener(leAdapteurDeMouse);
    addMouseListener(leAdapteurDeMouse);
    addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyChar());
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

      }
    });
  }

  void selectCandidate() {
    if (selectedPiece != selectionCandidate) {
      selectedPiece = selectionCandidate;
      repaint();
    }
    requestFocus();
  }

  void mouseOver(Point mousePos) {
    final Point mousePosLocal = new Point(mousePos.x - frameWidth, mousePos.y - frameWidth);
    final int x = mousePosLocal.x / gridWidth;
    final int y = mousePosLocal.y / gridWidth;
    final Piece piece = (
      ((mousePosLocal.x >= 0) && (mousePosLocal.y >= 0))
      ? field.pieceAt(x, y) : null
    );

    if (piece != selectionCandidate) {
      selectionCandidate = piece;
      repaint();
    }
  }


  @Override
  public void setField(Field newField) {
    super.setField(newField);
    selectedPiece = null;
    selectionCandidate = null;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (selectedPiece != null) {
      drawFrame(g, selectedPiece, 4);
    }
    if ((selectionCandidate != null) && (selectionCandidate != selectedPiece)) {
      drawFrame(g, selectionCandidate, 2);
    }
  }

  private void drawFrame(Graphics g, Piece selectedPiece, int lineWidth) {
    final int x = frameWidth + (gridWidth * selectedPiece.getLeftX() + 2);
    final int y = frameWidth + (gridWidth * selectedPiece.getTopY()) + 2;
    final int width = (gridWidth * selectedPiece.getWidth()) - 4;
    final int height = (gridWidth * selectedPiece.getHeight()) - 4;

    g.setColor(Color.RED);
    for (int i = 0; i < lineWidth; i++) {
      g.drawRoundRect(x + i, y + i, width - (2 * i), height - (2 * i), 20 - (2 * i), 20 - (2 * i));
    }
  }

}

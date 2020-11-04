package com.bensler.woopu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import com.bensler.woopu.model.Direction;
import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.Piece;

public class InteractiveFieldComponent extends FieldComponent {

  private final static Map<Integer, Direction> keyCodeDirectionMap = Map.of(
    KeyEvent.VK_UP,    Direction.NORTH,
    KeyEvent.VK_RIGHT, Direction.EAST,
    KeyEvent.VK_DOWN,  Direction.SOUTH,
    KeyEvent.VK_LEFT,  Direction.WEST
  );

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
      public void mouseExited(MouseEvent e) {
        mouseOver(new Point(-1, -1));
      }

      @Override
      public void mouseClicked(MouseEvent evt) {
        selectCandidate();
      }
    };

    setFocusable(true);
    addMouseMotionListener(leAdapteurDeMouse);
    addMouseListener(leAdapteurDeMouse);
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        final Direction direction = keyCodeDirectionMap.get(e.getKeyCode());

        if (direction != null) {
          if (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) {
            System.out.println("Crtl+" + direction);
          }
        }
      }
    });
    addFocusListener(new FocusListener() {
      @Override
      public void focusLost(FocusEvent e) {
        repaint();
      }

      @Override
      public void focusGained(FocusEvent e) {
        repaint();
      }
    });
  }

  void selectCandidate() {
    if (selectedPiece != selectionCandidate) {
      selectedPiece = selectionCandidate;
      repaint();
    }
    grabFocus();
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
    System.out.println(isFocusOwner());
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

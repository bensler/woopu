package com.bensler.woopu;

import static com.bensler.woopu.ui.anim.AnimationTask.FRAME_RATE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.stream.Collectors;

import com.bensler.woopu.model.Direction;
import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.Piece;
import com.bensler.woopu.ui.anim.AnimationProgress;
import com.bensler.woopu.ui.anim.AnimationTask;

public class InteractiveFieldComponent extends FieldComponent {

  private final static Map<Integer, Direction> keyCodeDirectionMap = Map.of(
    KeyEvent.VK_UP,    Direction.NORTH,
    KeyEvent.VK_RIGHT, Direction.EAST,
    KeyEvent.VK_DOWN,  Direction.SOUTH,
    KeyEvent.VK_LEFT,  Direction.WEST
  );

  private   final Timer timer;

  private AnimationProgress<MovingPiece> animation;

  private Piece selectedPiece;
  private Piece selectionCandidate;

  public InteractiveFieldComponent(float gridScaleFactor, ImageSource anImgSrc, Field aField) {
    super(gridScaleFactor, anImgSrc, aField);
    timer = new Timer();

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

        if (
          (direction != null)
          && (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK)
          && (selectedPiece != null)
          && field.arePositionsFree(direction.getNewlyOccupiedPositions(selectedPiece))
        ) {
          movePiece(selectedPiece, direction);
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

  void movePiece(Piece pieceToMove, Direction direction) {
    final List<Piece> newPieces = field.pieces().filter(piece -> (piece != pieceToMove)).collect(Collectors.toList());
    final Point newPosition = direction.getNewPosition(pieceToMove);
    final Piece newPiece = new Piece(pieceToMove.type, newPosition.x, newPosition.y);
    final AnimationTask task;

    newPieces.add(newPiece);
    task = new AnimationTask(new AnimationProgress<>(
      AnimationProgress.ATAN_TRANSFORMER,
      new MovingPiece(this, pieceToMove, direction),
      thisAnimation -> {animation = thisAnimation;},
      thisAnimation -> {paintImmediately(getBounds());},
      thisAnimation -> {
        animation = null;
        setField(new Field(newPieces));
        selectedPiece = newPiece;
      }
    ), 500, FRAME_RATE);
    timer.scheduleAtFixedRate(task, 0, task.getMsPerFrame());
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
    selectedPiece = null;
    selectionCandidate = null;
    super.setField(newField);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (animation != null) {
      animation.getContext().paint(this, g, animation.getProgressRatio());
    } else {
      if (selectedPiece != null) {
        drawFrame(g, selectedPiece, 4);
      }
      if ((selectionCandidate != null) && (selectionCandidate != selectedPiece)) {
        drawFrame(g, selectionCandidate, 2);
      }
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

  static class MovingPiece {

    private final Piece movingPiece;
    private final Rectangle clippingRect;
    private final Direction direction;

    public MovingPiece(InteractiveFieldComponent fieldComp, Piece aMovingPiece, Direction aDirection) {
      final int gridWidth = fieldComp.gridWidth;
      final Rectangle clipGrid;

      movingPiece = aMovingPiece;
      direction = aDirection;
      clipGrid = new Rectangle(
        Math.min(movingPiece.getLeftX(), movingPiece.getLeftX() + direction.getDeltaX()),
        Math.min(movingPiece.getTopY(), movingPiece.getTopY() + direction.getDeltaY()),
        movingPiece.getWidth() + Math.abs(direction.getDeltaX()),
        movingPiece.getHeight() + Math.abs(direction.getDeltaY())
      );
      clippingRect = new Rectangle(
        fieldComp.frameWidth + (gridWidth * clipGrid.x),
        fieldComp.frameWidth + (gridWidth * clipGrid.y),
        gridWidth * clipGrid.width,
        gridWidth * clipGrid.height
      );
    }

    void paint(InteractiveFieldComponent fieldComp, Graphics g, float progressRatio) {
      final int gridWidth = fieldComp.gridWidth;
      final Shape oldClip = g.getClip();

      try {
        g.setClip(clippingRect);
        fieldComp.paintBackground(g);
        fieldComp.paintPiece(
          g, movingPiece.type,
          fieldComp.frameWidth + (gridWidth * movingPiece.getLeftX()) + Math.round(gridWidth * progressRatio * direction.getDeltaX()),
          fieldComp.frameWidth + (gridWidth * movingPiece.getTopY()) + Math.round(gridWidth * progressRatio * direction.getDeltaY())
        );
      } finally {
        g.setClip(oldClip);
      }
    }

  }

}

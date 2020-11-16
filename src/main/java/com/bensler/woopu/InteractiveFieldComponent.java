package com.bensler.woopu;

import static com.bensler.woopu.ui.anim.AnimationTask.FRAME_RATE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.stream.Collectors;

import javax.swing.event.AncestorEvent;

import com.bensler.woopu.model.Direction;
import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.Piece;
import com.bensler.woopu.ui.AncestorAdapter;
import com.bensler.woopu.ui.anim.AnimationProgress;
import com.bensler.woopu.ui.anim.AnimationTask;

public class InteractiveFieldComponent extends FieldComponent {

  final static Map<Integer, Direction> keyCodeDirectionMap = Map.of(
    KeyEvent.VK_UP,    Direction.NORTH,
    KeyEvent.VK_RIGHT, Direction.EAST,
    KeyEvent.VK_DOWN,  Direction.SOUTH,
    KeyEvent.VK_LEFT,  Direction.WEST
  );

  private   final Timer timer;

  private AnimationProgress<MovingPiece> animation;

  private Piece selectedPiece;
  private Point selection;

  public InteractiveFieldComponent(float gridScaleFactor, ImageSource anImgSrc, Field aField) {
    super(gridScaleFactor, anImgSrc, aField);
    timer = new Timer();

    addAncestorListener(new AncestorAdapter() {
      @Override
      public void ancestorRemoved(AncestorEvent event) {
        timer.cancel();
      }

    });
    final MouseAdapter leAdapteurDeMouse = new MouseEventSource(this);

    setFocusable(true);
    addMouseMotionListener(leAdapteurDeMouse);
    addMouseListener(leAdapteurDeMouse);
    addKeyListener(new KeyEventSource(this));
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

  void moveSelectedPiece(Direction direction) {
   if (
     (selectedPiece != null)
      && field.arePositionsFree(direction.getNewlyOccupiedPositions(selectedPiece))
    ) {
      movePiece(selectedPiece, direction);
    }
  }

  public void moveSelection(Direction direction) {
    if (selection != null) {
      final Point newSelection = direction.getNewPosition(selection);

      if (field.isInField(newSelection)) {
        selectedPiece = field.pieceAt(selection = newSelection);
        repaint();
      }
    }
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
      thisAnimation -> animation = thisAnimation,
      thisAnimation -> paintImmediately(getBounds()),
      thisAnimation -> animationFinished(newPieces, newPiece, direction.getNewPosition(selection))
    ), 500, FRAME_RATE);
    timer.scheduleAtFixedRate(task, 0, task.getMsPerFrame());
  }

  void animationFinished(List<Piece> newPieces, Piece newPiece, Point newSelection) {
    animation = null;
    setField(new Field(newPieces));
    selectedPiece = newPiece;
    selection = newSelection;
  }

  void mouseClicked(Point mousePos) {
    final Point selectionCandidate = new Point(
      ((mousePos.x - frameWidth) / gridSize),
      ((mousePos.y - frameWidth) / gridSize)
    );

    if (field.isInField(selectionCandidate)) {
      final Piece piece = field.pieceAt(selectionCandidate);

      selection = selectionCandidate;
      if (piece != null) {
        selectedPiece = piece;
      }
      repaint();
    }
    grabFocus();
  }

  @Override
  public void setField(Field newField) {
    selectedPiece = null;
    selection = null;
    super.setField(newField);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (isInAnimation()) {
      animation.getContext().paint(this, g, animation.getProgressRatio());
    } else {
      drawSelection(g, 0, 0);
    }
  }

  private void drawSelection(Graphics g, int deltaXPix, int deltaYPix) {
    if (selection != null) {
      final Rectangle pixBounds = getGridPixBounds(selection.x, selection.y, 1, 1);

      pixBounds.x += deltaXPix;
      pixBounds.y += deltaYPix;
      drawFrame(g, pixBounds, 8, 2);
    }
  }

  @Override
  protected Rectangle paintPiece(Graphics g, Piece piece, int deltaXPix, int deltaYPix) {
    final Rectangle pixBounds = super.paintPiece(g, piece, deltaXPix, deltaYPix);

    if (selectedPiece == piece) {
      drawFrame(g, pixBounds, 2, 4);
    }
    return pixBounds;
  }

  private void drawFrame(Graphics g, Rectangle pixRect, int inset, int lineWidth) {
    pixRect.x += inset;
    pixRect.y += inset;
    pixRect.width -= (2 * inset);
    pixRect.height -= (2 * inset);
    g.setColor(Color.RED);
    for (int i = 0; i < lineWidth; i++) {
      g.drawRoundRect(
        pixRect.x + i, pixRect.y + i, pixRect.width - (2 * i), pixRect.height - (2 * i),
        20 - (2 * i), 20 - (2 * i)
      );
    }
  }

  public boolean isInAnimation() {
    return (animation != null);
  }

  static class MovingPiece {

    private final Piece movingPiece;
    private final Rectangle clippingRect;
    private final Direction direction;

    public MovingPiece(InteractiveFieldComponent fieldComp, Piece aMovingPiece, Direction aDirection) {
      final int gridWidth = fieldComp.gridSize;
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
      final int gridWidth = fieldComp.gridSize;
      final Shape oldClip = g.getClip();
      final int deltaXPix = Math.round(gridWidth * progressRatio * direction.getDeltaX());
      final int deltaYPix = Math.round(gridWidth * progressRatio * direction.getDeltaY());

      try {
        g.setClip(clippingRect);
        fieldComp.paintBackground(g);
        fieldComp.paintPiece(g, movingPiece, deltaXPix, deltaYPix);
        fieldComp.drawSelection(g, deltaXPix, deltaYPix);
      } finally {
        g.setClip(oldClip);
      }
    }

  }

}

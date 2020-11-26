package com.bensler.woopu;

import static com.bensler.woopu.ui.anim.AnimationTask.FRAME_RATE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.util.Timer;

import javax.swing.event.AncestorEvent;

import com.bensler.woopu.model.Direction;
import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.Piece;
import com.bensler.woopu.ui.AncestorAdapter;
import com.bensler.woopu.ui.anim.AnimationProgress;
import com.bensler.woopu.ui.anim.AnimationTask;

public class InteractiveFieldComponent extends FieldComponent {

  private final History history;
  private final Timer timer;

  private AnimationProgress<MovingPiece> animation;

  private Piece selectedPiece;
  private Point selection;

  public InteractiveFieldComponent(float gridScaleFactor, ImageSource anImgSrc, Field aField) {
    super(gridScaleFactor, anImgSrc, aField);
    history = new History();
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

  public void moveSelectedPiece(Direction direction) {
   if (
     (selectedPiece != null)
      && field.arePositionsFree(selectedPiece, direction)
    ) {
      history.push(new Move(selectedPiece, direction));
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

  public void undo() {
    if (!history.isEmpty()) {
      final Move lastMove = history.pop();

      movePiece(lastMove.getMovingPiece(), lastMove.getDirection().getOpposite());
    }
  }

  private void movePiece(Piece pieceToMove, Direction direction) {
    final AnimationTask task;
    final Point oldPiecePos = field.getPosition(pieceToMove);
    final Point newPiecePos = direction.getNewPosition(field.getPosition(pieceToMove));

    if ((selection != null) && field.isPieceCovering(pieceToMove, selection)) {
      selection = direction.getNewPosition(selection);
    }
    field.setPosition(pieceToMove, newPiecePos);
    task = new AnimationTask(new AnimationProgress<>(
      AnimationProgress.ATAN_TRANSFORMER,
      new MovingPiece(this, oldPiecePos, pieceToMove),
      thisAnimation -> animation = thisAnimation,
      thisAnimation -> paintImmediately(thisAnimation.getContext().getClippingRect()),
      thisAnimation -> animation = null
    ), 500, FRAME_RATE);
    timer.scheduleAtFixedRate(task, 0, task.getMsPerFrame());
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
    if (animation != null) {
      animation.getContext().paint(this, g, animation.getProgressRatio());
    } else {
      paintSelection(g, 0, 0);
    }
  }

  private void paintSelection(Graphics g, int deltaXPix, int deltaYPix) {
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

  public void finishAnimation() {
    if (animation != null) {
      animation.terminatedImmediately();
    }
  }

  static class MovingPiece {

    private final int deltaX;
    private final int deltaY;
    private final Piece piece;
    private final Rectangle clippingRect;

    public MovingPiece(InteractiveFieldComponent fieldComp, Point oldPiecePos, Piece movingPiece) {
      final Point position = fieldComp.getPiecePosition(piece = movingPiece);
      deltaX = oldPiecePos.x - position.x;
      deltaY = oldPiecePos.y - position.y;
      final int gridWidth = fieldComp.gridSize;
      final Rectangle clipGrid;

      clipGrid = new Rectangle(
        Math.min(piece.getLeftX(position), oldPiecePos.x),
        Math.min(piece.getTopY(position), oldPiecePos.y),
        piece.getWidth() + Math.abs(oldPiecePos.x - position.x),
        piece.getHeight() + Math.abs(oldPiecePos.y - position.y)
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
      final int deltaXPix = Math.round(gridWidth * (1 - progressRatio) * deltaX);
      final int deltaYPix = Math.round(gridWidth * (1 - progressRatio) * deltaY);

      try {
        g.setClip(clippingRect);
        fieldComp.paintBackground(g);
        fieldComp.paintPiece(g, piece, deltaXPix, deltaYPix);
        fieldComp.paintSelection(g, deltaXPix, deltaYPix);
      } finally {
        g.setClip(oldClip);
      }
    }

    Rectangle getClippingRect() {
      return clippingRect;
    }

  }

}

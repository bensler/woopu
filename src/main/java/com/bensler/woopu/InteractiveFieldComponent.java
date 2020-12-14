package com.bensler.woopu;

import static com.bensler.woopu.ui.anim.AnimationTask.FRAME_RATE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.util.List;
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

  private AnimationProgress<MovingPieces> animation;

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
    if (selectedPiece != null) {
      final List<Piece> piecesToMove = field.getPiecesToMove(selectedPiece, direction);

      if (!piecesToMove.isEmpty()) {
        movePieces(history.push(new Move(piecesToMove, direction)));
      }
    }
  }

  public void moveSelection(Direction direction) {
    if (selection != null) {
      final Point newSelection = direction.getNewPosition(selection);

      if (field.isInField(newSelection)) {
        selectedPiece = field.pieceAt(selection = newSelection).orElse(null);
        repaint();
      }
    }
  }

  public void undo() {
    if (!history.isEmpty()) {
      movePieces(history.pop().createOppositeMove());
    }
  }

  private void movePieces(Move move) {
    final AnimationTask task;
    final Direction direction = move.getDirection();

    if (selection != null) {
      for (Piece piece : move) {
        if (field.isPieceCovering(piece, selection)) {
          selection = direction.getNewPosition(selection);
          break;
        }
      }
    }
    move.forEachPiece(piece -> field.setPosition(
      piece, direction.getNewPosition(field.getPosition(piece))
    ));
    task = new AnimationTask(new AnimationProgress<>(
      AnimationProgress.ATAN_TRANSFORMER,
      new MovingPieces(this, move),
      thisAnimation -> animation = thisAnimation,
      thisAnimation -> paintImmediately(thisAnimation.getContext().getClippingRect()),
      thisAnimation -> moveDone()
    ), 500, FRAME_RATE);
    timer.scheduleAtFixedRate(task, 0, task.getMsPerFrame());
  }

  void moveDone() {
    animation = null;
    if (field.isWinningPosition()) {
      System.out.println("Hossa!");
    }
  }

  void mouseClicked(Point mousePos) {
    final Point selectionCandidate = new Point(
      ((mousePos.x - frameWidth) / gridSize),
      ((mousePos.y - frameWidth) / gridSize)
    );

    if (field.isInField(selectionCandidate)) {
      field.pieceAt(selection = selectionCandidate).ifPresent(
        newlySelectedPiece -> selectedPiece = newlySelectedPiece
      );
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

  void paintSelection(Graphics g, int deltaXPix, int deltaYPix) {
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

}

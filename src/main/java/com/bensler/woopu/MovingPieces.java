package com.bensler.woopu;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import com.bensler.woopu.model.Direction;

class MovingPieces implements Paintable {

  private final Move move;
  private final Set<Rectangle> clippingRects;
  private final Rectangle clippingRect;

  public MovingPieces(InteractiveFieldComponent fieldComp, Move aMove) {
    final Direction direction = (move = aMove).getDirection();
    final int deltaX = -direction.getDeltaX();
    final int deltaY = -direction.getDeltaY();
    final int gridWidth = fieldComp.gridSize;

    clippingRects = new HashSet<>();
    move.forEach(piece -> {
      final Point position = fieldComp.getPiecePosition(piece);
      final Point oldPiecePos = new Point(position.x + deltaX, position.y + deltaY);
      final Rectangle clipGrid = new Rectangle(
        Math.min(piece.getLeftX(position), oldPiecePos.x),
        Math.min(piece.getTopY(position), oldPiecePos.y),
        piece.getWidth() + Math.abs(oldPiecePos.x - position.x),
        piece.getHeight() + Math.abs(oldPiecePos.y - position.y)
      );

      clippingRects.add(new Rectangle(
        fieldComp.frameWidth + (gridWidth * clipGrid.x),
        fieldComp.frameWidth + (gridWidth * clipGrid.y),
        gridWidth * clipGrid.width,
        gridWidth * clipGrid.height
      ));
    });

    final BiConsumer<Rectangle, Rectangle> combiner = (acc, rect) -> {
      acc.setRect(acc.union(rect));
    };
    clippingRect = clippingRects.stream().collect(
      () -> new Rectangle(-1, -1), combiner, combiner
    );
  }

  @Override
  public void paint(InteractiveFieldComponent fieldComp, Graphics g, float progressRatio) {
    final Shape oldClip = g.getClip();

    try {
      clippingRects.forEach(rect -> {
        g.setClip(rect);
        fieldComp.paintBackground(g);
      });
    } finally {
      g.setClip(oldClip);
    }

    final Direction direction = move.getDirection();
    final int deltaX = -direction.getDeltaX();
    final int deltaY = -direction.getDeltaY();
    final int gridWidth = fieldComp.gridSize;
    final int deltaXPix = Math.round(gridWidth * (1 - progressRatio) * deltaX);
    final int deltaYPix = Math.round(gridWidth * (1 - progressRatio) * deltaY);

    move.forEachPiece(piece -> fieldComp.paintPiece(g, piece, deltaXPix, deltaYPix));
    fieldComp.paintSelection(g, deltaXPix, deltaYPix);
  }

  @Override
  public Rectangle getClippingRect() {
    return clippingRect;
  }

}
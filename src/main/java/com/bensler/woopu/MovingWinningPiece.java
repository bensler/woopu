package com.bensler.woopu;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import com.bensler.woopu.model.Direction;
import com.bensler.woopu.model.Piece;
import com.bensler.woopu.model.PieceType;

class MovingWinningPiece implements Paintable {

  private final Piece bluePiece;
  private final Rectangle clippingRect;

  public MovingWinningPiece(InteractiveFieldComponent fieldComp, Piece aBluePiece) {
    if (aBluePiece.type != PieceType.BLUE) {
      throw new IllegalArgumentException(String.format("%s is not of type %s", aBluePiece, PieceType.BLUE));
    }
    bluePiece = aBluePiece;

    final int gridWidth = fieldComp.gridSize;
    final Point position = fieldComp.getPiecePosition(bluePiece);
    final Rectangle clipGrid = new Rectangle(
      bluePiece.getLeftX(position),bluePiece.getTopY(position),
      bluePiece.getWidth(), bluePiece.getHeight()
    );

    clippingRect = new Rectangle(
      fieldComp.frameWidth + (gridWidth * clipGrid.x),
      fieldComp.frameWidth + (gridWidth * clipGrid.y),
      gridWidth * clipGrid.width,
      gridWidth * clipGrid.height
    );
  }

  @Override
  public void paint(InteractiveFieldComponent fieldComp, Graphics g, float progressRatio) {
    final Shape oldClip = g.getClip();

    try {
      g.setClip(clippingRect);
      fieldComp.paintBackground(g);
    } finally {
      g.setClip(oldClip);
    }

    final Direction direction = Direction.SOUTH;
    final int deltaX = 2 * direction.getDeltaX();
    final int deltaY = 2 * direction.getDeltaY();
    final int gridWidth = fieldComp.gridSize;
    final int deltaXPix = Math.round(gridWidth * progressRatio * deltaX);
    final int deltaYPix = Math.round(gridWidth * progressRatio * deltaY);

    fieldComp.paintPiece(g, bluePiece, deltaXPix, deltaYPix);
    fieldComp.paintSelection(g, deltaXPix, deltaYPix);
  }

  @Override
  public Rectangle getClippingRect() {
    return clippingRect;
  }

}
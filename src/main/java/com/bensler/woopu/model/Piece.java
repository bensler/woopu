package com.bensler.woopu.model;

import java.awt.Point;

public class Piece {

  public final PieceType type;

  public Piece(PieceType aType) {
    type = aType;
  }

  public int getWidth() {
    return type.width;
  }

  public int getHeight() {
    return type.height;
  }

  public int getLeftX(Point position) {
    return position.x;
  }

  public int getRightX(Point position) {
    return position.x + type.width - 1;
  }

  public int getTopY(Point position) {
    return position.y;
  }

  public int getBottomY(Point position) {
    return position.y + type.height - 1;
  }

  @Override
  public String toString() {
    return String.format("Piece[%s]", type.name());
  }

  public boolean intersectWith(Point position, Piece other, Point otherPosition) {
    return (
         (getRightX(position)  >= otherPosition.x)
      && (position.x <= other.getRightX(otherPosition))
      && (getBottomY(position) >= otherPosition.y)
      && (position.y <= other.getBottomY(otherPosition))
    );
  }

  public boolean covers(Point ownPosition, Point positionToTest) {
    return (
         (positionToTest.x >= getLeftX(ownPosition))
      && (positionToTest.x <= getRightX(ownPosition))
      && (positionToTest.y >= getTopY(ownPosition))
      && (positionToTest.y <= getBottomY(ownPosition))
    );
  }

}

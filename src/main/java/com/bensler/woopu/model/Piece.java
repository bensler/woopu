package com.bensler.woopu.model;

public class Piece {

  public final PieceType type;
  public final int x;
  public final int y;

  public Piece(PieceType aType, int aX, int aY) {
    type = aType;
    x = aX;
    y = aY;
  }

  public int getLeftX() {
    return x;
  }

  public int getRightX() {
    return x + type.width - 1;
  }

  public int getTopY() {
    return y;
  }

  public int getBottomY() {
    return y + type.height - 1;
  }

  @Override
  public String toString() {
    return String.format("Piece[%s, %d, %d]", type.name(), x, y);
  }

  public boolean intersectWith(Piece other) {
    return (
         (getRightX()  >= other.x) && (x <= other.getRightX())
      && (getBottomY() >= other.y) && (y <= other.getBottomY())
    );
  }

}

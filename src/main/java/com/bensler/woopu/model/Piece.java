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
    return x + type.width;
  }

  public int getTopY() {
    return y;
  }

  public int getBottomY() {
    return y + type.height;
  }

  @Override
  public String toString() {
    return String.format("Piece[%s, %d, %d]", type.name(), x, x);
  }

  public boolean intersectWith(Piece other) {
    return (
         (x + type.width >= other.x) && (x <= other.x + other.type.width)
      || (y + type.height >= other.y) && (y <= other.y + other.type.height)
    );
  }

}

package com.bensler.woopu.model;

/**
 * Represents all types of pieces in this game.
 */
public enum PieceType {

  YELLOW(1, 1),         // yellow dot 1x1
  RED_HORIZONTAL(2, 1), // red dot 2x1
  RED_VERTICAL(1, 2),   // red dot 1x2
  BLUE(2, 2);           // blue dot 2x2

  public final int gridWidth;
  public final int gridHeight;

  PieceType(int aGridWidth, int aGridHeight) {
    gridWidth = aGridWidth;
    gridHeight = aGridHeight;
  }

}

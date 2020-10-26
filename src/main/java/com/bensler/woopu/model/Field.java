package com.bensler.woopu.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a state of play field. It ensures that {@link Piece}s do not
 * overlap and field borders are honored.
 *
 * Coordinate System originates at the top left corner and starts with (0, 0).
 */
public class Field {

  private final int width;
  private final int height;
  private final List<Piece> pieces;

  public Field(int aWidth, int aHeight, Piece... newPieces) {
    width = aWidth;
    height = aHeight;
    pieces = new ArrayList<>();
    for (Piece newPiece : newPieces) {
      if (
        (newPiece.getLeftX() < 0)
        || (newPiece.getRightX() >= width)
        || (newPiece.getTopY() < 0)
        || (newPiece.getBottomY() >= height)
      ) {
        throw new IllegalArgumentException(String.format("%s does not fit into Field[%d, %d]", newPiece, width, height));
      }

    }
  }

}

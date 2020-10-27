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

  public static final int WIDTH  = 4;
  public static final int HEIGHT = 5;

  private final List<Piece> pieces;

  public Field(Piece... newPieces) {
    pieces = new ArrayList<>();
    for (Piece newPiece : newPieces) {
      if (
        (newPiece.getLeftX() < 0)
        || (newPiece.getRightX() >= WIDTH)
        || (newPiece.getTopY() < 0)
        || (newPiece.getBottomY() >= HEIGHT)
      ) {
        throw new IllegalArgumentException(String.format("%s does not fit into Field[%d, %d]", newPiece, WIDTH, HEIGHT));
      }
      for (Piece piece : pieces) {
        if (piece.intersectWith(newPiece)) {
          throw new IllegalArgumentException(String.format("%s does not fit into Field[%d, %d]", newPiece, WIDTH, HEIGHT));
        }
      }
      pieces.add(newPiece);
    }
  }

}

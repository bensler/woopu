package com.bensler.woopu.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
    this(Arrays.asList(newPieces));
  }

  public Field(List<Piece> newPieces) {
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
          throw new IllegalArgumentException(String.format("%s overlaps with %s", newPiece, piece));
        }
      }
      pieces.add(newPiece);
    }
  }

  public boolean isInField(Point position) {
    return (
      (position.x >= 0) && (position.x < WIDTH)
      && (position.y >= 0) && (position.y < HEIGHT)
    );
  }

  /** copy constructor */
  public Field(Field field) {
    this(field.pieces);
  }

  public Stream<Piece> pieces() {
    return pieces.stream();
  }

  public Piece pieceAt(Point position) {
    return pieces().filter(
      piece -> piece.covers(position.x, position.y)
    ).findFirst().orElse(null);
  }

  public boolean arePositionsFree(List<Point> positions) {
    for (Point position : positions) {
      if (!isPositionFree(position)) {
        return false;
      }
    }
    return true;
  }

  public boolean isPositionFree(Point position) {
    final boolean inField = (
      (position.x >= 0) && (position.x < WIDTH)
      && (position.y >= 0) && (position.y < HEIGHT)
    );

    if (!inField) {
      return false;
    }
    for (Piece piece : pieces) {
      if (piece.covers(position.x, position.y)) {
        return false;
      }
    }
    return true;
  }

}

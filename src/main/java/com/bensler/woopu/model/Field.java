package com.bensler.woopu.model;

import java.awt.Point;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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

  private final Map<Piece, Point> piecePositions;

  public Field() {
    super();
    piecePositions = new HashMap<>();
  }

  /** copy constructor */
  public Field(Field field) {
    this();
    field.piecePositions.entrySet().stream().forEach(
      piPo -> addPiece(new Piece(piPo.getKey().type), piPo.getValue())
    );
  }

  public Field addPiece(Piece newPiece, Point newPosition) {
    if (piecePositions.containsKey(newPiece)) {
      throw new IllegalArgumentException(String.format("%s is already part of this field", newPiece));
    }
    if (
      (newPiece.getLeftX(newPosition) < 0)
      || (newPiece.getRightX(newPosition) >= WIDTH)
      || (newPiece.getTopY(newPosition) < 0)
      || (newPiece.getBottomY(newPosition) >= HEIGHT)
    ) {
      throw new IllegalArgumentException(String.format("%s does not fit into Field[%d, %d]", newPiece, WIDTH, HEIGHT));
    }
    for (Piece piece : piecePositions.keySet()) {
      if (piece.intersectWith(getPosition(piece), newPiece, newPosition)) {
        throw new IllegalArgumentException(String.format("%s overlaps with %s", newPiece, piece));
      }
    }
    piecePositions.put(newPiece, newPosition);
    return this;
  }

  public boolean isInField(Point position) {
    return (
      (position.x >= 0) && (position.x < WIDTH)
      && (position.y >= 0) && (position.y < HEIGHT)
    );
  }

  public Stream<Piece> pieces() {
    return piecePositions.keySet().stream();
  }

  public Piece pieceAt(Point position) {
    return piecePositions.entrySet().stream().filter(
      piPo -> piPo.getKey().covers(piPo.getValue(), position)
    ).findFirst().orElseGet(
      () -> new SimpleImmutableEntry<>(null, null)
    ).getKey();
  }

  public Point getPosition(Piece piece) {
    return piecePositions.get(piece);
  }

  public boolean arePositionsFree(Piece piece, Direction direction) {
    for (Point position : direction.getNewlyOccupiedPositions(piece, getPosition(piece))) {
      if (!isPositionFree(position)) {
        return false;
      }
    }
    return true;
  }

  public boolean isPositionFree(Point positionToTest) {
    if (!isInField(positionToTest)) {
      return false;
    }
    for (Entry<Piece, Point> piPo : piecePositions.entrySet()) {
      if (piPo.getKey().covers(piPo.getValue(), positionToTest)) {
        return false;
      }
    }
    return true;
  }

  public void setPosition(Piece piece, Point newPosition) {
    if (!piecePositions.containsKey(piece)) {
      throw new IllegalArgumentException(String.format("unknown piece %s", piece));
    }
    piecePositions.put(piece, newPosition);
  }

}

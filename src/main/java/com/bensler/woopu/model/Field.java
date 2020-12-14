package com.bensler.woopu.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
    if (
      (newPiece.getLeftX(newPosition) < 0)
      || (newPiece.getRightX(newPosition) >= WIDTH)
      || (newPiece.getTopY(newPosition) < 0)
      || (newPiece.getBottomY(newPosition) >= HEIGHT)
    ) {
      throw new IllegalArgumentException(String.format("%s does not fit into Field[%d, %d]", newPiece, WIDTH, HEIGHT));
    }
    for (Piece piece : piecePositions.keySet()) {
      if (
        (piece != newPiece)
        && piece.intersectWith(getPosition(piece), newPiece, newPosition)
      ) {
        throw new IllegalArgumentException(String.format("%s overlaps with %s", newPiece, piece));
      }
    }
    piecePositions.put(newPiece, newPosition);
    return this;
  }

  public boolean isWinningPosition() {
    final Optional<Piece> bluePiece = getBluePiece();

    return bluePiece.isPresent() && getPosition(bluePiece.get()).equals(new Point(1, HEIGHT - PieceType.BLUE.height));
  }

  public Optional<Piece> getBluePiece() {
    return piecePositions.entrySet().stream()
      .map(entry -> entry.getKey())
      .filter(piece -> (piece.type == PieceType.BLUE))
      .findFirst();
  }

  public void setPosition(Piece piece, Point newPosition) {
    if (!piecePositions.containsKey(piece)) {
      throw new IllegalArgumentException(String.format("unknown piece %s", piece));
    }
    addPiece(piece, newPosition);
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

  public Optional<Piece> pieceAt(Point position) {
    return piecePositions.entrySet().stream().filter(
      piPo -> piPo.getKey().covers(piPo.getValue(), position)
    ).findFirst().flatMap(entry -> Optional.of(entry.getKey()));
  }

  public Point getPosition(Piece piece) {
    return piecePositions.get(piece);
  }

  public List<Piece> getPiecesToMove(Piece piece, Direction direction) {
    final Set<Piece> othersToMove = new HashSet<>();

    for (Point position : direction.getNewlyOccupiedPositions(piece, getPosition(piece))) {
      if (isInField(position)) {
        pieceAt(position).ifPresent(dependingOnPiece -> othersToMove.add(dependingOnPiece));
      } else {
        return List.of();
      }
    }
    if (othersToMove.isEmpty()) {
      return List.of(piece);
    } else {
      final List<Piece> result = new ArrayList<>();

      for (Piece dependingOnPiece : othersToMove) {
        final List<Piece> subResult = getPiecesToMove(dependingOnPiece, direction);

        if (subResult.isEmpty()) {
          return List.of();
        } else {
          result.addAll(subResult);
        }
      }
      result.add(piece);
      return List.copyOf(result);
    }
  }

  public boolean isPieceCovering(Piece piece, Point positionToTest) {
    return piece.covers(piecePositions.get(piece), positionToTest);
  }

  public void removePiece(Piece bluePiece) {
    piecePositions.remove(bluePiece);
  }

}

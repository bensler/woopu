package com.bensler.woopu.model;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum Direction {
  NORTH (0, -1, Piece::getWidth) {
    @Override
    protected Point getNewlyOccupiedPosition(Piece piece, Point position, int newPositionIndex) {
      return new Point(piece.getLeftX(position) + newPositionIndex, piece.getTopY(position) - 1);
    }
  },
  EAST (1, 0, Piece::getHeight) {
    @Override
    protected Point getNewlyOccupiedPosition(Piece piece, Point position, int newPositionIndex) {
      return new Point(piece.getRightX(position) + 1, piece.getTopY(position) + newPositionIndex);
    }
  },
  SOUTH (0, 1, Piece::getWidth) {
    @Override
    protected Point getNewlyOccupiedPosition(Piece piece, Point position, int newPositionIndex) {
      return new Point(piece.getLeftX(position) + newPositionIndex, piece.getBottomY(position) + 1);
    }
  },
  WEST (-1, 0, Piece::getHeight) {
    @Override
    protected Point getNewlyOccupiedPosition(Piece piece, Point position, int newPositionIndex) {
      return new Point(piece.getLeftX(position) - 1, piece.getTopY(position) + newPositionIndex);
    }
  };

  private static final Map<Direction, Direction> oppositeDirection = Map.of(
    NORTH, SOUTH,
    SOUTH, NORTH,
    EAST, WEST,
    WEST, EAST
  );

  private final int deltaX;
  private final int deltaY;

  private final Function<Piece, Integer> amountProvider;

  Direction(int aDeltaX, int aDeltaY, Function<Piece, Integer> anAmountProvider) {
    deltaX = aDeltaX;
    deltaY = aDeltaY;
    amountProvider = anAmountProvider;
  }

  public List<Point> getNewlyOccupiedPositions(Piece piece, Point position) {
    final Point[] result = (Point[]) Array.newInstance(Point.class, amountProvider.apply(piece));

    for (int i = 0; i < result.length; i++) {
      result[i] = getNewlyOccupiedPosition(piece, position, i);
    }
    return Arrays.asList(result);
  }

  protected abstract Point getNewlyOccupiedPosition(Piece piece, Point position, int newPositionIndex);

  public int getDeltaX() {
    return deltaX;
  }

  public int getDeltaY() {
    return deltaY;
  }

  public Direction getOpposite() {
    return oppositeDirection.get(this);
  }

  public Point getNewPosition(Point position) {
    return new Point(position.x + deltaX, position.y  + deltaY);
  }

}

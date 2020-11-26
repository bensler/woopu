package com.bensler.woopu.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum Direction {
  NORTH (0, -1) {
    @Override
    public List<Point> getNewlyOccupiedPositions(Piece piece, Point position) {
      final int y = piece.getTopY(position) - 1;
      final List<Point> result = new ArrayList<>(piece.getWidth());

      for (int i = 0; i < piece.getWidth(); i++) {
        result.add(new Point(piece.getLeftX(position) + i, y));
      }
      return result;
    }
  },
  EAST (1, 0) {
    @Override
    public List<Point> getNewlyOccupiedPositions(Piece piece, Point position) {
      final int x = piece.getRightX(position) + 1;
      final List<Point> result = new ArrayList<>(piece.getHeight());

      for (int i = 0; i < piece.getHeight(); i++) {
        result.add(new Point(x, piece.getTopY(position) + i));
      }
      return result;
    }
  },
  SOUTH (0, 1) {
    @Override
    public List<Point> getNewlyOccupiedPositions(Piece piece, Point position) {
      final int y = piece.getBottomY(position) + 1;
      final List<Point> result = new ArrayList<>(piece.getWidth());

      for (int i = 0; i < piece.getWidth(); i++) {
        result.add(new Point(piece.getLeftX(position) + i, y));
      }
      return result;
    }
  },
  WEST (-1, 0) {
    @Override
    public List<Point> getNewlyOccupiedPositions(Piece piece, Point position) {
      final int x = piece.getLeftX(position) - 1;
      final List<Point> result = new ArrayList<>(piece.getHeight());

      for (int i = 0; i < piece.getHeight(); i++) {
        result.add(new Point(x, piece.getTopY(position) + i));
      }

      return result;
    }
  };

  private final int deltaX;
  private final int deltaY;

  Direction(int aDeltaX, int aDeltaY) {
    deltaX = aDeltaX;
    deltaY = aDeltaY;
  }

  private static final Map<Direction, Direction> oppositeDirection = Map.of(
    NORTH, SOUTH,
    SOUTH, NORTH,
    EAST, WEST,
    WEST, EAST
  );

  public abstract List<Point> getNewlyOccupiedPositions(Piece piece, Point position);

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

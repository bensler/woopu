package com.bensler.woopu.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public enum Direction {
  NORTH (0, -1) {
    @Override
    public List<Point> getNewlyOccupiedPositions(Piece piece) {
      final int y = piece.getTopY() - 1;
      final List<Point> result = new ArrayList<>(piece.getWidth());

      for (int i = 0; i < piece.getWidth(); i++) {
        result.add(new Point(piece.getLeftX() + i, y));
      }
      return result;
    }
  },
  EAST (1, 0) {
    @Override
    public List<Point> getNewlyOccupiedPositions(Piece piece) {
      final int x = piece.getRightX() + 1;
      final List<Point> result = new ArrayList<>(piece.getHeight());

      for (int i = 0; i < piece.getHeight(); i++) {
        result.add(new Point(x, piece.getTopY() + i));
      }
      return result;
    }
  },
  SOUTH (0, 1) {
    @Override
    public List<Point> getNewlyOccupiedPositions(Piece piece) {
      final int y = piece.getBottomY() + 1;
      final List<Point> result = new ArrayList<>(piece.getWidth());

      for (int i = 0; i < piece.getWidth(); i++) {
        result.add(new Point(piece.getLeftX() + i, y));
      }
      return result;
    }
  },
  WEST (-1, 0) {
    @Override
    public List<Point> getNewlyOccupiedPositions(Piece piece) {
      final int x = piece.getLeftX() - 1;
      final List<Point> result = new ArrayList<>(piece.getHeight());

      for (int i = 0; i < piece.getHeight(); i++) {
        result.add(new Point(x, piece.getTopY() + i));
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

  public abstract List<Point> getNewlyOccupiedPositions(Piece piece);

  public int getDeltaX() {
    return deltaX;
  }

  public int getDeltaY() {
    return deltaY;
  }

  public Point getNewPosition(Piece piece) {
    return new Point(piece.getLeftX() + deltaX, piece.getTopY() + deltaY);
  }

}

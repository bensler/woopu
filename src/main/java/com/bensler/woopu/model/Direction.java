package com.bensler.woopu.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public enum Direction {
  NORTH {
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
  EAST {
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
  SOUTH {
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
  WEST {
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

  public abstract List<Point> getNewlyOccupiedPositions(Piece piece);

}

package com.bensler.woopu;

import static com.bensler.woopu.model.PieceType.BLUE;
import static com.bensler.woopu.model.PieceType.RED_HORIZONTAL;
import static com.bensler.woopu.model.PieceType.RED_VERTICAL;
import static com.bensler.woopu.model.PieceType.YELLOW;

import java.awt.Point;

import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.Piece;

/**
 * Collection of {@link Field}s to start with.
 */
public enum Fields {

  A(new Field()
    .addPiece(new Piece(RED_VERTICAL),   new Point(0, 0))
    .addPiece(new Piece(RED_VERTICAL),   new Point(1, 0))
    .addPiece(new Piece(RED_VERTICAL),   new Point(3, 0))
    .addPiece(new Piece(RED_VERTICAL),   new Point(0, 2))
    .addPiece(new Piece(BLUE),           new Point(1, 2))
    .addPiece(new Piece(RED_VERTICAL),   new Point(3, 2))
    .addPiece(new Piece(YELLOW),         new Point(0, 4))
    .addPiece(new Piece(YELLOW),         new Point(1, 4))
    .addPiece(new Piece(YELLOW),         new Point(2, 4))
    .addPiece(new Piece(YELLOW),         new Point(3, 4))
  ),

  B(new Field()
    .addPiece(new Piece(RED_HORIZONTAL), new Point(1, 0))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(0, 1))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(2, 1))
    .addPiece(new Piece(RED_VERTICAL),   new Point(0, 2))
    .addPiece(new Piece(BLUE),           new Point(1, 2))
    .addPiece(new Piece(RED_VERTICAL),   new Point(3, 2))
    .addPiece(new Piece(YELLOW),         new Point(0, 4))
    .addPiece(new Piece(YELLOW),         new Point(1, 4))
    .addPiece(new Piece(YELLOW),         new Point(2, 4))
    .addPiece(new Piece(YELLOW),         new Point(3, 4))
  ),

  C(new Field()
    .addPiece(new Piece(YELLOW),         new Point(0, 0))
    .addPiece(new Piece(YELLOW),         new Point(0, 1))
    .addPiece(new Piece(BLUE),           new Point(1, 0))
    .addPiece(new Piece(YELLOW),         new Point(3, 0))
    .addPiece(new Piece(YELLOW),         new Point(3, 1))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(1, 2))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(0, 3))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(2, 3))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(0, 4))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(2, 4))
  ),

  D(new Field()
    .addPiece(new Piece(YELLOW),         new Point(1, 0))
    .addPiece(new Piece(YELLOW),         new Point(2, 0))
    .addPiece(new Piece(YELLOW),         new Point(0, 1))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(1, 1))
    .addPiece(new Piece(YELLOW),         new Point(3, 1))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(0, 2))
    .addPiece(new Piece(BLUE),           new Point(0, 3))
    .addPiece(new Piece(RED_VERTICAL),   new Point(2, 2))
    .addPiece(new Piece(RED_VERTICAL),   new Point(3, 2))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(2, 4))
  ),

  E(new Field()
    .addPiece(new Piece(RED_HORIZONTAL), new Point(0, 0))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(2, 0))
    .addPiece(new Piece(RED_VERTICAL),   new Point(0, 1))
    .addPiece(new Piece(BLUE),           new Point(1, 1))
    .addPiece(new Piece(RED_VERTICAL),   new Point(3, 1))
    .addPiece(new Piece(YELLOW),         new Point(0, 3))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(1, 3))
    .addPiece(new Piece(YELLOW),         new Point(3, 3))
    .addPiece(new Piece(YELLOW),         new Point(0, 4))
    .addPiece(new Piece(YELLOW),         new Point(3, 4))
  ),

  F(new Field()
    .addPiece(new Piece(RED_HORIZONTAL), new Point(0, 0))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(2, 0))
    .addPiece(new Piece(BLUE),           new Point(1, 1))
    .addPiece(new Piece(RED_VERTICAL),   new Point(0, 2))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(1, 3))
    .addPiece(new Piece(RED_VERTICAL),   new Point(3, 2))
    .addPiece(new Piece(YELLOW),         new Point(0, 4))
    .addPiece(new Piece(YELLOW),         new Point(1, 4))
    .addPiece(new Piece(YELLOW),         new Point(2, 4))
    .addPiece(new Piece(YELLOW),         new Point(3, 4))
  ),

  G(new Field()
    .addPiece(new Piece(RED_VERTICAL),   new Point(0, 0))
    .addPiece(new Piece(BLUE),           new Point(1, 0))
    .addPiece(new Piece(YELLOW),         new Point(3, 0))
    .addPiece(new Piece(YELLOW),         new Point(3, 1))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(1, 2))
    .addPiece(new Piece(YELLOW),         new Point(0, 3))
    .addPiece(new Piece(YELLOW),         new Point(0, 4))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(1, 3))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(1, 4))
    .addPiece(new Piece(RED_VERTICAL),   new Point(3, 3))
  ),

  H(new Field()
    .addPiece(new Piece(YELLOW),         new Point(0, 1))
    .addPiece(new Piece(BLUE),           new Point(1, 0))
    .addPiece(new Piece(YELLOW),         new Point(3, 1))
    .addPiece(new Piece(RED_VERTICAL),   new Point(0, 2))
    .addPiece(new Piece(YELLOW),         new Point(1, 2))
    .addPiece(new Piece(YELLOW),         new Point(2, 2))
    .addPiece(new Piece(RED_VERTICAL),   new Point(3, 2))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(1, 3))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(0, 4))
    .addPiece(new Piece(RED_HORIZONTAL), new Point(2, 4))
  );

  private final Field field;

  Fields(Field aField) {
    field = aField;
  }

  public Field getField() {
    return new Field(field);
  }

}

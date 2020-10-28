package com.bensler.woopu;

import static com.bensler.woopu.model.PieceType.BLUE;
import static com.bensler.woopu.model.PieceType.RED_HORIZONTAL;
import static com.bensler.woopu.model.PieceType.RED_VERTICAL;
import static com.bensler.woopu.model.PieceType.YELLOW;

import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.Piece;

/**
 * Collection of {@link Field}s to start with.
 */
public enum Fields {

  A(new Field(
    new Piece(RED_VERTICAL,  0, 0),
    new Piece(RED_VERTICAL,  1, 0),
    new Piece(RED_VERTICAL,  3, 0),
    new Piece(RED_VERTICAL,  0, 2),
    new Piece(BLUE,          1, 2),
    new Piece(RED_VERTICAL,  3, 2),
    new Piece(YELLOW,        0, 4),
    new Piece(YELLOW,        1, 4),
    new Piece(YELLOW,        2, 4),
    new Piece(YELLOW,        3, 4)
  )),

  B(new Field(
    new Piece(RED_HORIZONTAL, 1, 0),
    new Piece(RED_HORIZONTAL, 0, 1),
    new Piece(RED_HORIZONTAL, 2, 1),
    new Piece(RED_VERTICAL,   0, 2),
    new Piece(BLUE,           1, 2),
    new Piece(RED_VERTICAL,   3, 2),
    new Piece(YELLOW,         0, 4),
    new Piece(YELLOW,         1, 4),
    new Piece(YELLOW,         2, 4),
    new Piece(YELLOW,         3, 4)
  )),

  C(new Field(
    new Piece(YELLOW,         0, 0),
    new Piece(YELLOW,         0, 1),
    new Piece(BLUE,           1, 0),
    new Piece(YELLOW,         3, 0),
    new Piece(YELLOW,         3, 1),
    new Piece(RED_HORIZONTAL, 1, 2),
    new Piece(RED_HORIZONTAL, 0, 3),
    new Piece(RED_HORIZONTAL, 2, 3),
    new Piece(RED_HORIZONTAL, 0, 4),
    new Piece(RED_HORIZONTAL, 2, 4)
  )),

  D(new Field(
    new Piece(YELLOW,         1, 0),
    new Piece(YELLOW,         2, 0),
    new Piece(YELLOW,         0, 1),
    new Piece(RED_HORIZONTAL, 1, 1),
    new Piece(YELLOW,         3, 1),
    new Piece(RED_HORIZONTAL, 0, 2),
    new Piece(BLUE,           0, 3),
    new Piece(RED_VERTICAL,   2, 2),
    new Piece(RED_VERTICAL,   3, 2),
    new Piece(RED_HORIZONTAL, 2, 4)
  )),

  E(new Field(
    new Piece(RED_HORIZONTAL, 0, 0),
    new Piece(RED_HORIZONTAL, 2, 0),
    new Piece(RED_VERTICAL,   0, 1),
    new Piece(BLUE,           1, 1),
    new Piece(RED_VERTICAL,   3, 1),
    new Piece(YELLOW,         0, 3),
    new Piece(RED_HORIZONTAL, 1, 3),
    new Piece(YELLOW,         3, 3),
    new Piece(YELLOW,         0, 4),
    new Piece(YELLOW,         3, 4)
  )),

  F(new Field(
    new Piece(RED_HORIZONTAL, 0, 0),
    new Piece(RED_HORIZONTAL, 2, 0),
    new Piece(BLUE,           1, 1),
    new Piece(RED_VERTICAL,   0, 2),
    new Piece(RED_HORIZONTAL, 1, 3),
    new Piece(RED_VERTICAL,   3, 2),
    new Piece(YELLOW,         0, 4),
    new Piece(YELLOW,         1, 4),
    new Piece(YELLOW,         2, 4),
    new Piece(YELLOW,         3, 4)
  )),

  G(new Field(
    new Piece(RED_VERTICAL,   0, 0),
    new Piece(BLUE,           1, 0),
    new Piece(YELLOW,         3, 0),
    new Piece(YELLOW,         3, 1),
    new Piece(RED_HORIZONTAL, 1, 2),
    new Piece(YELLOW,         0, 3),
    new Piece(YELLOW,         0, 4),
    new Piece(RED_HORIZONTAL, 1, 3),
    new Piece(RED_HORIZONTAL, 1, 4),
    new Piece(RED_VERTICAL,   3, 3)
  )),

  H(new Field(
    new Piece(YELLOW,         0, 1),
    new Piece(BLUE,           1, 0),
    new Piece(YELLOW,         3, 1),
    new Piece(RED_VERTICAL,   0, 2),
    new Piece(YELLOW,         1, 2),
    new Piece(YELLOW,         2, 2),
    new Piece(RED_VERTICAL,   3, 2),
    new Piece(RED_HORIZONTAL, 1, 3),
    new Piece(RED_HORIZONTAL, 0, 4),
    new Piece(RED_HORIZONTAL, 2, 4)
  ));

  final Field field;

  Fields(Field aField) {
    field = aField;
  }

  public Field getField() {
    return new Field(field);
  }

}

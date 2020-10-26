package com.bensler.woopu.model;

import static com.bensler.woopu.model.PieceType.BLUE;
import static com.bensler.woopu.model.PieceType.RED_HORIZONTAL;
import static com.bensler.woopu.model.PieceType.RED_VERTICAL;
import static com.bensler.woopu.model.PieceType.YELLOW;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class FieldTest {

  @Test
  public void testFieldBorders() {
    try {
      new Field(4, 5, new Piece(YELLOW, -1, 0));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field(4, 5, new Piece(RED_HORIZONTAL, 3, 0));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field(4, 5, new Piece(RED_VERTICAL, 2, -1));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field(4, 5, new Piece(BLUE, 1, 4));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
  }

}

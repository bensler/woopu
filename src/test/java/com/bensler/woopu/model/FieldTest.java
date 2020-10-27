package com.bensler.woopu.model;

import static com.bensler.woopu.model.PieceType.BLUE;
import static com.bensler.woopu.model.PieceType.RED_HORIZONTAL;
import static com.bensler.woopu.model.PieceType.RED_VERTICAL;
import static com.bensler.woopu.model.PieceType.YELLOW;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;

public class FieldTest {

  @Test
  public void testFieldBorders() {
    try {
      new Field(new Piece(YELLOW, -1, 0));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field(new Piece(RED_HORIZONTAL, 3, 0));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field(new Piece(RED_VERTICAL, 2, -1));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field(new Piece(BLUE, 1, 4));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
  }

  @Test
  public void testPieceIntersection() {
    try {
      new Field(
        new Piece(BLUE, 1, 1),
        new Piece(YELLOW, 2, 1)
      );
      fail();
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field(
        new Piece(BLUE, 2, 1),
        new Piece(RED_HORIZONTAL, 1, 2)
      );
      fail();
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field(
        new Piece(BLUE, 2, 2),
        new Piece(RED_VERTICAL, 2, 1)
      );
      fail();
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field(
        new Piece(BLUE, 2, 2),
        new Piece(YELLOW, 2, 3)
      );
      fail();
    } catch (IllegalArgumentException iae) { /* iae expected */ }
  }

}

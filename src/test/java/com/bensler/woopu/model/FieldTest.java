package com.bensler.woopu.model;

import static com.bensler.woopu.model.PieceType.BLUE;
import static com.bensler.woopu.model.PieceType.RED_HORIZONTAL;
import static com.bensler.woopu.model.PieceType.RED_VERTICAL;
import static com.bensler.woopu.model.PieceType.YELLOW;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.awt.Point;

import org.junit.Test;

public class FieldTest {

  @Test
  public void testFieldBorders() {
    try {
      new Field().addPiece(new Piece(YELLOW), new Point(-1, 0));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field().addPiece(new Piece(RED_HORIZONTAL), new Point(3, 0));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field().addPiece(new Piece(RED_VERTICAL), new Point(2, -1));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field().addPiece(new Piece(BLUE), new Point(1, 4));
      assertFalse(true);
    } catch (IllegalArgumentException iae) { /* iae expected */ }
  }

  @Test
  public void testPieceIntersection() {
    try {
      new Field()
      .addPiece(new Piece(BLUE), new Point(1, 1))
      .addPiece(new Piece(YELLOW), new Point(2, 1));
      fail();
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field()
      .addPiece(new Piece(BLUE), new Point(2, 1))
      .addPiece(new Piece(RED_HORIZONTAL), new Point(1, 2));
      fail();
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field()
      .addPiece(new Piece(BLUE), new Point(2, 2))
      .addPiece(new Piece(RED_VERTICAL), new Point(2, 1));
      fail();
    } catch (IllegalArgumentException iae) { /* iae expected */ }
    try {
      new Field()
      .addPiece(new Piece(BLUE), new Point(2, 2))
      .addPiece(new Piece(YELLOW), new Point(2, 3));
      fail();
    } catch (IllegalArgumentException iae) { /* iae expected */ }
  }

}

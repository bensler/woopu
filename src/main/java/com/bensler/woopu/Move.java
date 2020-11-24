package com.bensler.woopu;

import com.bensler.woopu.model.Direction;
import com.bensler.woopu.model.Piece;

public class Move {

  private final Piece movingPiece;
  private final Direction direction;

  public Move(Piece aMovingPiece, Direction aDirection) {
    movingPiece = aMovingPiece;
    direction = aDirection;
  }

  public Piece getMovingPiece() {
    return movingPiece;
  }

  public Direction getDirection() {
    return direction;
  }

}

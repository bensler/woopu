package com.bensler.woopu;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import com.bensler.woopu.model.Direction;
import com.bensler.woopu.model.Piece;

public class Move implements Iterable<Piece>{

  private final List<Piece> movingPieces;
  private final Direction direction;

  public Move(List<Piece> newMovingPieces, Direction aDirection) {
    movingPieces = List.copyOf(newMovingPieces);
    direction = aDirection;
  }

  public void forEachPiece(Consumer<? super Piece> action) {
    movingPieces.forEach(action);
  }

  public Direction getDirection() {
    return direction;
  }

  public Move createOppositeMove() {
    return new Move(movingPieces, direction.getOpposite());
  }

  @Override
  public Iterator<Piece> iterator() {
    return movingPieces.iterator();
  }

}

package com.bensler.woopu;

import java.util.LinkedList;

public class History {

  private final LinkedList<Move> moves;

  public History() {
    moves = new LinkedList<>();
  }

  /**
   * @return the given <code>move</code>
   */
  public Move push(Move move) {
    moves.add(move);
    return move;
  }

  public Move pop() {
    return moves.removeLast();
  }

  public boolean isEmpty() {
    return moves.isEmpty();
  }

}

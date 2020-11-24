package com.bensler.woopu;

import java.util.LinkedList;

public class History {

  private final LinkedList<Move> moves;

  public History() {
    moves = new LinkedList<>();
  }

  public void push(Move move) {
    moves.add(move);
  }

  public Move pop() {
    return moves.removeLast();
  }

  public boolean isEmpty() {
    return moves.isEmpty();
  }

}

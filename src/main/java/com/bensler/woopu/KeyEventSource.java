package com.bensler.woopu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;

import com.bensler.woopu.model.Direction;

public class KeyEventSource extends KeyAdapter {

  private final static Map<Integer, Direction> keyCodeDirectionMap = Map.of(
    KeyEvent.VK_UP,    Direction.NORTH,
    KeyEvent.VK_RIGHT, Direction.EAST,
    KeyEvent.VK_DOWN,  Direction.SOUTH,
    KeyEvent.VK_LEFT,  Direction.WEST
  );

  private final InteractiveFieldComponent fieldComponent;

  public KeyEventSource(InteractiveFieldComponent aFieldComponent) {
    fieldComponent = aFieldComponent;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    fieldComponent.finishAnimation();
    final Direction direction = keyCodeDirectionMap.get(e.getKeyCode());

    if (direction != null) {
      if (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) {
        fieldComponent.moveSelectedPiece(direction);
      }
      if (e.getModifiersEx() == 0) {
        fieldComponent.moveSelection(direction);
      }
    }
    if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE) && (e.getModifiersEx() == 0)) {
      fieldComponent.undo();
    }
  }

}
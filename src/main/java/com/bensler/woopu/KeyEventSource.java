package com.bensler.woopu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.bensler.woopu.model.Direction;

public class KeyEventSource extends KeyAdapter {

  private final InteractiveFieldComponent fieldComponent;

  public KeyEventSource(InteractiveFieldComponent aFieldComponent) {
    fieldComponent = aFieldComponent;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    final Direction direction = InteractiveFieldComponent.keyCodeDirectionMap.get(e.getKeyCode());

    if (
      (direction != null)
      && (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK)
      && (!fieldComponent.isInAnimation())
    ) {
      fieldComponent.moveSelectedPiece(direction);
    }
  }

}
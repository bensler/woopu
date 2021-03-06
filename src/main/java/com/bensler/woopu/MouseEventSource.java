package com.bensler.woopu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEventSource extends MouseAdapter {

  private final InteractiveFieldComponent fieldComponent;

  MouseEventSource(InteractiveFieldComponent aFieldComponent) {
    fieldComponent = aFieldComponent;
  }

  @Override
  public void mouseClicked(MouseEvent evt) {
    fieldComponent.finishAnimation();
    fieldComponent.mouseClicked(evt.getPoint());
  }

}
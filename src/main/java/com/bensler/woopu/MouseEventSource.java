package com.bensler.woopu;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEventSource extends MouseAdapter {

  private final InteractiveFieldComponent fieldComponent;

  MouseEventSource(InteractiveFieldComponent aFieldComponent) {
    fieldComponent = aFieldComponent;
  }

  @Override
  public void mouseMoved(MouseEvent evt) {
    if (!fieldComponent.isInAnimation()) {
      fieldComponent.mouseOver(evt.getPoint());
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (!fieldComponent.isInAnimation()) {
      fieldComponent.mouseOver(new Point(-1, -1));
    }
  }

  @Override
  public void mouseClicked(MouseEvent evt) {
    if (!fieldComponent.isInAnimation()) {
      fieldComponent.selectCandidate();
    }
  }

}
package com.bensler.woopu.util;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JComponent;

/**
 * Implements the {@link Icon} interface by drawing a random {@link JComponent}.
 */
public class ComponentIconAdapter implements Icon {

  private final JComponent component;

  public ComponentIconAdapter(JComponent aComponent) {
    component = aComponent;
    component.setSize(component.getPreferredSize());
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    try (final Resource<Graphics> g2 = new Resource<>(g.create(), subG -> subG.dispose())) {
      g2.resource.translate(x, y);
      component.paint(g2.resource);
    }
  }

  @Override
  public int getIconWidth() {
    return component.getSize().width;
  }

  @Override
  public int getIconHeight() {
    return component.getSize().height;
  }

}

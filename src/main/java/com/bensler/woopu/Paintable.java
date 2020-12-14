package com.bensler.woopu;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Paintable {

  Rectangle getClippingRect();

  void paint(InteractiveFieldComponent fieldComp, Graphics g, float progressRatio);

}

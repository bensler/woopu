package com.bensler.woopu;

import java.io.IOException;

public class Piece {

  public final static ImageResource backgroundImg;
  public final static ImageResource pieceYellowImg;
  public final static ImageResource pieceRedHorizontal;
  public final static ImageResource pieceRedVertical;
  public final static ImageResource pieceBlue;

  static {
    try {
      backgroundImg = new ImageResource(App.class, "background.png", 1645, 2000);
      pieceYellowImg = new ImageResource(App.class, "piece-yellow.png", 360, 360);
      pieceRedHorizontal = new ImageResource(App.class, "piece-red-horizontal.png", 720, 360);
      pieceRedVertical = new ImageResource(App.class, "piece-red-vertical.png", 360, 720);
      pieceBlue = new ImageResource(App.class, "piece-blue.png", 720, 720);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}

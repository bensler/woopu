package com.bensler.woopu;

import java.io.IOException;

public class PieceImages {

  public final static int GRID_SIZE = 360;

  public final static ImageResource backgroundImg;
  public final static ImageResource pieceYellowImg;
  public final static ImageResource pieceRedHorizontal;
  public final static ImageResource pieceRedVertical;
  public final static ImageResource pieceBlue;

  static {
    try {
      backgroundImg = new ImageResource(App.class, "background.png", 1640, 2000);
      pieceYellowImg = new ImageResource(App.class, "piece-yellow.png", GRID_SIZE, GRID_SIZE);
      pieceRedHorizontal = new ImageResource(App.class, "piece-red-horizontal.png", 2 * GRID_SIZE, GRID_SIZE);
      pieceRedVertical = new ImageResource(App.class, "piece-red-vertical.png", GRID_SIZE, 2 * GRID_SIZE);
      pieceBlue = new ImageResource(App.class, "piece-blue.png", 2 * GRID_SIZE, 2 * GRID_SIZE);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}

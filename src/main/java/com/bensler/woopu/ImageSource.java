package com.bensler.woopu;

import java.awt.Image;
import java.io.IOException;

import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.PieceType;

/**
 * Provides loaded image resources as static fields. Image sizes are checked on loading.
 */
public class ImageSource {

  public final static int ORIGINAL_IMG_FACTOR = 10;

  public final static int GRID_SIZE  = 36;
  public final static int FRAME_SIZE = 10;

  final ImageResource windowIcon;
  final ImageResource backgroundImg;
  final ImageResource pieceYellowImg;
  final ImageResource pieceRedHorizontalImg;
  final ImageResource pieceRedVerticalImg;
  final ImageResource pieceBlueImg;

  ImageSource() {
    try {
      windowIcon = new ImageResource(App.class, "window-icon.png", 385, 339);
      backgroundImg = new ImageResource(
        App.class, "background.png",
        getBackgroundImgWidth(ORIGINAL_IMG_FACTOR),
        ((2 * FRAME_SIZE) + (Field.HEIGHT * GRID_SIZE)) * ORIGINAL_IMG_FACTOR
      );
      pieceYellowImg =        loadImageResource("piece-yellow.png",         PieceType.YELLOW);
      pieceRedHorizontalImg = loadImageResource("piece-red-horizontal.png", PieceType.RED_HORIZONTAL);
      pieceRedVerticalImg =   loadImageResource("piece-red-vertical.png",   PieceType.RED_VERTICAL);
      pieceBlueImg =          loadImageResource("piece-blue.png",           PieceType.BLUE);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private int getBackgroundImgWidth(int factor) {
    return ((2 * FRAME_SIZE) + (Field.WIDTH  * GRID_SIZE)) * factor;
  }

  Image getBackgroundImage(int factor) {
    return backgroundImg.getScaledInstance(getBackgroundImgWidth(factor));
  }

  private ImageResource loadImageResource(String resourceName, PieceType pieceType) throws IOException {
    final int actualGridSize = GRID_SIZE * ORIGINAL_IMG_FACTOR;

    return new ImageResource(
      App.class, resourceName, pieceType.width * actualGridSize, pieceType.height * actualGridSize
    );
  }

}

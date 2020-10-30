package com.bensler.woopu;

import java.awt.Image;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.bensler.woopu.model.Field;
import com.bensler.woopu.model.PieceType;

/**
 * Provides loaded image resources as static fields. Image sizes are checked on loading.
 * TODO
 */
public class ImageSource {

  public final static int ORIGINAL_IMG_FACTOR = 10;

  public final static int GRID_SIZE  = 36;
  public final static int FRAME_SIZE = 10;

  final ImageResource windowIcon;
  final FieldImageSource originalImages;

  ImageSource() {
    try {
      windowIcon = new ImageResource(App.class, "window-icon.png", 385, 339);
      final HashMap<PieceType, Image> imgMap = new HashMap<>();

      putImage("piece-yellow.png",         PieceType.YELLOW, imgMap);
      putImage("piece-red-horizontal.png", PieceType.RED_HORIZONTAL, imgMap);
      putImage("piece-red-vertical.png",   PieceType.RED_VERTICAL, imgMap);
      putImage("piece-blue.png",           PieceType.BLUE, imgMap);
      originalImages = new FieldImageSource(
        new ImageResource(
          App.class, "background.png",
          getBackgroundImgWidth(ORIGINAL_IMG_FACTOR),
          ((2 * FRAME_SIZE) + (Field.HEIGHT * GRID_SIZE)) * ORIGINAL_IMG_FACTOR
        ).getImage(),
        imgMap
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private ImageResource loadImageResource(String resourceName, PieceType pieceType) throws IOException {
    final int actualGridSize = GRID_SIZE * ORIGINAL_IMG_FACTOR;

    return new ImageResource(
      App.class, resourceName, pieceType.width * actualGridSize, pieceType.height * actualGridSize
    );
  }

  private void putImage(String fileName, PieceType type, Map<PieceType, Image> targetMap) throws IOException {
    targetMap.put(type, loadImageResource(fileName, type).getImage());
  }

  private int getBackgroundImgWidth(float factor) {
    return Math.round(((2 * FRAME_SIZE) + (Field.WIDTH  * GRID_SIZE)) * factor);
  }

  FieldImageSource getFieldImageSource(float scaleFactor) {
    return new FieldImageSource(
      originalImages.getBackgroundImage().getScaledInstance(getBackgroundImgWidth(scaleFactor), -1, Image.SCALE_SMOOTH),
      Arrays.asList(PieceType.values()).stream().collect(Collectors.toMap(
        type -> type,
        type -> originalImages.getPieceImage(type).getScaledInstance(
          Math.round(GRID_SIZE * scaleFactor * type.width), -1, Image.SCALE_SMOOTH
        )
      ))
    );
  }

}

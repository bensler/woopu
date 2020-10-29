package com.bensler.woopu;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.bensler.woopu.model.PieceType;

/**
 * TODO
 */
public class FieldImageSource {

  private final Image backgroundImg;
  private final Map<PieceType, Image> pieceImages;
  private final Map<PieceType, ImageIcon> pieceImageIcons;

  FieldImageSource(Image aBackgroundImg, Map<PieceType, Image> scaledPieceImages) {
    backgroundImg = aBackgroundImg;
    pieceImages = new HashMap<>(scaledPieceImages);
    pieceImageIcons = new HashMap<>();
  }

  Image getBackgroundImage() {
    return backgroundImg;
  }

  Image getPieceImage(PieceType pieceType) {
    return pieceImages.get(pieceType);
  }

  ImageIcon getPieceImageIcon(PieceType pieceType) {
    return pieceImageIcons.computeIfAbsent(pieceType, type -> new ImageIcon(getPieceImage(type)));
  }

}

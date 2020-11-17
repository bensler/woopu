package com.bensler.woopu;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.bensler.woopu.model.PieceType;

/**
 * Keeps an {@link Image} and an {@link ImageIcon} for each {@link PieceType} and background.
 * <p>
 * ImageIcons are created lazily from Images and are cached as they seem to be not exactly lightweight.
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

package com.bensler.woopu;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageResource {

  private final String resourceName;
  private final int width;
  private final int height;
  private final BufferedImage image;

  public ImageResource(
    Class<?> resourceParentClass, String aResourceName,
    int aWidth, int aHeight
  ) throws IOException {
    final URL resource = resourceParentClass.getResource(resourceName = aResourceName);

    if (resource == null) {
      throw new IllegalArgumentException(String.format(
        "Image resource \"%s\" of class \"%s\" not found.", resourceName, resourceParentClass.getName()
      ));
    }
    image = ImageIO.read(resource);

    final int imgWidth = image.getWidth();
    final int imgHeight = image.getHeight();

    if (imgWidth != (width = aWidth)) {
      throw new IllegalArgumentException(String.format(
        "Image resource \"%s\" width mismatch. Expected: %d but %d found.", resourceName, width, imgWidth
      ));
    }
    if (imgHeight != (height = aHeight)) {
      throw new IllegalArgumentException(String.format(
        "Image resource \"%s\" height mismatch. Expected: %d but %d found.", resourceName, height, imgHeight
      ));
    }
  }

  public Image getScaledInstance(int width) {
    return image.getScaledInstance(width, -1, Image.SCALE_SMOOTH);
  }

}

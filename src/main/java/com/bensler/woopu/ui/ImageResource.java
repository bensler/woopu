package com.bensler.woopu.ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/** TODO */
public class ImageResource {

  private final String resourceName;
  private final BufferedImage image;

  public ImageResource(
    Class<?> resourceParentClass, String aResourceName,
    int width, int height
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

    if (imgWidth != width) {
      throw new IllegalArgumentException(String.format(
        "Image resource \"%s\" width mismatch. Expected: %d but %d found.", resourceName, width, imgWidth
      ));
    }
    if (imgHeight != height) {
      throw new IllegalArgumentException(String.format(
        "Image resource \"%s\" height mismatch. Expected: %d but %d found.", resourceName, height, imgHeight
      ));
    }
  }

  public Image getScaledInstance(int width) {
    return image.getScaledInstance(width, -1, Image.SCALE_SMOOTH);
  }

  public BufferedImage getImage() {
    return image;
  }

}

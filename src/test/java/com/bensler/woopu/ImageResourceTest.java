package com.bensler.woopu;

import org.junit.Test;

public class ImageResourceTest {

  /**
   * Just trigger {@link PieceImages} class loading, which checks availability
   * of image resources and their sizes.
   */
  @Test
  public void loadImageResources() {
    PieceImages.backgroundImg.hashCode();
  }

}

package com.bensler.woopu;

import org.junit.Test;

public class ImageResourceTest {

  /**
   * Just trigger {@link ImageSource} class loading, which checks availability
   * of image resources and their sizes.
   */
  @Test
  public void loadImageResources() {
    ImageSource.backgroundImg.hashCode();
  }

}

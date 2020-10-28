package com.bensler.woopu;

import org.junit.Test;

import com.bensler.woopu.model.Field;

public class FieldsTest {

  /**
   * Just trigger {@link Fields} class loading, which checks consistency of contained {@link Field}s.
   */
  @Test
  public void loadImageResources() {
    Fields.values().hashCode();
  }

}

package com.bensler.woopu.util;

import java.awt.Graphics;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A Resource implementing {@link AutoCloseable} to make the try-with-resource
 * idiom useable with any other class (not implementing {@link AutoCloseable}).
 * <p>
 * Example: a {@link Graphics} object needs to be disposed after use. Usually
 * this would be achieved like that:
 * <pre>
 * final Graphics subG = g.create();
 *
 * try {
 *   subG.translate(x, y);
 *   paintComponent(subG);
 * } finally {
 *   subG.dispose();
 * }
 * </pre>
 * By wrapping any resource into a {@link Resource} object this can be done in
 * a shorter way:
 * <pre>
 * try (Resource<Graphics> g2 = new Resource<>(g.create(), subG -> subG.dispose())) {
 *   g2.resource.translate(x, y);
 *   paintComponent(g2.resource);
 * }
 * </pre>
 * Not sure if readability is really better ... .
 */
public class Resource<R> implements AutoCloseable {

  public  final R resource;
  private final Consumer<R> autoCloser;

  public Resource(R aResource, Consumer<R> anAutoCloser) {
    resource = aResource;
    autoCloser = anAutoCloser;
  }

  public Resource(Supplier<R> supplier, Consumer<R> autoClose) {
    this(supplier.get(), autoClose);
  }

  @Override
  public void close() {
    autoCloser.accept(resource);
  }

}
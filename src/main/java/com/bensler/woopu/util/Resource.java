package com.bensler.woopu.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

/** TODO
 * bla
 * <pre>
 * try (Resource<Graphics> g2 = new Resource<>(g.create(), subG -> subG.dispose())) {
 *   g2.resource.translate(x, y);
 *   paintComponent(g2.resource);
 * }
 * </pre>
 * bla
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
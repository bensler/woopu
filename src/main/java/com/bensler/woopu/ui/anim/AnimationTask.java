package com.bensler.woopu.ui.anim;

import java.lang.reflect.InvocationTargetException;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class AnimationTask extends TimerTask {

  public final static int FRAME_RATE = 30;

  private final AnimationProgress<?> progress;
  private final int maxFrameCount;
  private final int msPerFrame;

  private volatile boolean terminated;
  private int frameCount;

  public AnimationTask(AnimationProgress<?> anAnimationState, long durationMillies, int frameRate) {
    msPerFrame = Math.round(1000.0f / frameRate);
    maxFrameCount = Math.round(durationMillies / (float)msPerFrame);
    (progress = anAnimationState).setDurationNanos((maxFrameCount * msPerFrame) * 1_000_000);
    progress.setTask(this);
    terminated = false;
  }

  public int getMsPerFrame() {
    return msPerFrame;
  }

  synchronized void terminatedImmediately() {
    cancel();
    frameCount = maxFrameCount;
    run();
    terminated = true;
  }

  @Override
  public synchronized void run() {
    if (!terminated) {
      try {
        Runnable runnable = () -> {
          final boolean firstRun = (frameCount == 0);
          final boolean lastRun = (frameCount >= maxFrameCount);

          progress.beforePaint(firstRun, lastRun);
          progress.triggerPaint();
          progress.afterPaint(firstRun, lastRun);
          if (lastRun) {
            cancel();
          } else {
            frameCount++;
          }
        };
        if (SwingUtilities.isEventDispatchThread()) {
          runnable.run();
        } else {
          SwingUtilities.invokeAndWait(runnable);
        }
      } catch (InterruptedException ie) {
        // swallow intentionally
      } catch (InvocationTargetException ite) {
        ite.getCause().printStackTrace();
      }
    }
  }

}
package com.bensler.woopu.ui.anim;

import java.lang.reflect.InvocationTargetException;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class AnimationTask extends TimerTask {

  public final static int FRAME_RATE = 30;

  private final AnimationProgress<?> progress;
  private final int maxFrameCount;
  private final int msPerFrame;

  private int frameCount;

  public AnimationTask(AnimationProgress<?> anAnimationState, long durationMillies, int frameRate) {
    msPerFrame = Math.round(1000.0f / frameRate);
    maxFrameCount = Math.round(durationMillies / (float)msPerFrame);
    (progress = anAnimationState).setDurationNanos((maxFrameCount * msPerFrame) * 1_000_000);
  }

  public int getMsPerFrame() {
    return msPerFrame;
  }

  @Override
  public void run() {
    final boolean firstRun = (frameCount == 0);
    final boolean lastRun = (frameCount >= maxFrameCount);

    progress.beforePaint(firstRun, lastRun);
    try {
      SwingUtilities.invokeAndWait(() -> progress.triggerPaint());
    } catch (InterruptedException ie) {
      // swallow intentionally
    } catch (InvocationTargetException ite) {
      ite.getCause().printStackTrace();
    }
    progress.afterPaint(firstRun, lastRun);
    if (lastRun) {
      cancel();
    }
    frameCount++;
  }

}
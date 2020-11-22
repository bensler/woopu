package com.bensler.woopu.ui.anim;

import java.lang.reflect.InvocationTargetException;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class AnimationTask extends TimerTask {

  public final static int FRAME_RATE = 30;

  private final AnimationProgress<?> progress;
  private final int maxFrameCount;
  private final int msPerFrame;

  private boolean finishImmediately;
  private int frameCount;

  public AnimationTask(AnimationProgress<?> anAnimationState, long durationMillies, int frameRate) {
    msPerFrame = Math.round(1000.0f / frameRate);
    maxFrameCount = Math.round(durationMillies / (float)msPerFrame);
    (progress = anAnimationState).setDurationNanos((maxFrameCount * msPerFrame) * 1_000_000);
    progress.setTask(this);
    finishImmediately = false;
  }

  public int getMsPerFrame() {
    return msPerFrame;
  }

  void finishImmediately() {
    finishImmediately = true;
  }

  @Override
  public void run() {
    try {
      SwingUtilities.invokeAndWait(() -> {
        final boolean firstRun = (frameCount == 0);
        final boolean lastRun = (finishImmediately || (frameCount >= maxFrameCount));

        progress.beforePaint(firstRun, lastRun);
        progress.triggerPaint();
        progress.afterPaint(firstRun, lastRun);
        if (lastRun || finishImmediately) {
          cancel();
        }
        frameCount++;
      });
    } catch (InterruptedException ie) {
      // swallow intentionally
    } catch (InvocationTargetException ite) {
      ite.getCause().printStackTrace();
    }
  }

}
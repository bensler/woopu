package com.bensler.woopu.ui.anim;

import java.util.function.Consumer;
import java.util.function.Function;

public class AnimationProgress<CONTEXT> {

  public enum AnimationState {
    NEW {
      @Override
      float getProgressRatio(AnimationProgress<?> progress) {
        return 0.0f;
      }
    },
    ANIMATING {
      @Override
      float getProgressRatio(AnimationProgress<?> progress) {
        final long nanoDiff = (System.nanoTime() - progress.firstNanos);

        return Math.min(MAX_PROGRESS_RATIO, (nanoDiff / (float)progress.maxNanoDiff));
      }
    },
    DONE {
      @Override
      float getProgressRatio(AnimationProgress<?> progress) {
        return MAX_PROGRESS_RATIO;
      }
    };

    abstract float getProgressRatio(AnimationProgress<?> progress);

  }

  public final static float MAX_PROGRESS_RATIO = 1.0f;

  public static final Function<Float, Float> NOOP_TRANSFORMER = (ratio -> ratio);
  public static final Function<Float, Float> INVERSE_TRANSFORMER = (ratio -> (MAX_PROGRESS_RATIO - ratio));
  public static final Function<Float, Float> ATAN_TRANSFORMER = new Function<>() {

    private final double ATAN2 = Math.atan(2.0f);
    private final double TWO_ATAN2 = 2 * ATAN2;

    @Override
    public Float apply(Float f) {
      final float adjustedX = (f * 4) - 2;
      final double atan = Math.atan(adjustedX);

      return (float)((atan + ATAN2) / TWO_ATAN2);
    }
  };

  private final Function<Float, Float> ratioTransformer;
  private final CONTEXT context;
  private final Consumer<AnimationProgress<CONTEXT>> startListener;
  private final Consumer<AnimationProgress<CONTEXT>> painter;
  private final Consumer<AnimationProgress<CONTEXT>> doneListener;

  private AnimationState state;
  long firstNanos;
  long maxNanoDiff;

  public AnimationProgress(
    Function<Float, Float> aRatioTransformer,
    CONTEXT aContext,
    Consumer<AnimationProgress<CONTEXT>> aStartListener,
    Consumer<AnimationProgress<CONTEXT>> aPainter,
    Consumer<AnimationProgress<CONTEXT>> aDoneListener
  ) {
    ratioTransformer = aRatioTransformer;
    context = aContext;
    startListener = aStartListener;
    painter = aPainter;
    doneListener = aDoneListener;
  }

  void beforePaint(boolean firstRun, boolean lastRun) {
    if (firstRun) {
      state = AnimationState.NEW;
      firstNanos = System.nanoTime();
      startListener.accept(this);
    } else {
      if (lastRun) {
        state = AnimationState.DONE;
      } else {
        state = AnimationState.ANIMATING;
      }
    }
  }

  void afterPaint(boolean firstRun, boolean lastRun) {
    if (lastRun) {
      doneListener.accept(this);
    }
  }

  public Consumer<AnimationProgress<CONTEXT>> getStartListener() {
    return startListener;
  }

  public Consumer<AnimationProgress<CONTEXT>> getDoneListener() {
    return doneListener;
  }

  public Consumer<AnimationProgress<CONTEXT>> getPainter() {
    return painter;
  }

  public CONTEXT getContext() {
    return context;
  }

  void setDurationNanos(long durationNanos) {
    maxNanoDiff = durationNanos;
  }

  public AnimationState getState() {
    return state;
  }

  public float getProgressRatio() {
    return ratioTransformer.apply(state.getProgressRatio(this));
  }

  public Function<Float, Float> getRatioTransformer() {
    return ratioTransformer;
  }

  void triggerPaint() {
    painter.accept(this);
  }

}

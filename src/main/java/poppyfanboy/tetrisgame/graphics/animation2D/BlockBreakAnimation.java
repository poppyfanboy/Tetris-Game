package poppyfanboy.tetrisgame.graphics.animation2D;

import poppyfanboy.tetrisgame.graphics.Animation;

/**
 * Animation that is used for breaking the blocks in the full filled line.
 */
public class BlockBreakAnimation implements Animation {
    private static final double FINAL_ROTATION_ANGLE = -Math.PI / 3;
    private static final double SCALE_COEFFICIENT = 0.3;

    private final double startScale, startAngle;
    private final Animated2D object;

    private int duration;
    private int currentDuration = 0;

    public BlockBreakAnimation(Animated2D object,
            double startAngle, double startScale, int duration) {
        this.object = object;
        this.duration = duration;
        this.startAngle = startAngle;
        this.startScale = startScale;
    }

    @Override
    public void tick() {
        if (!finished()) {
            currentDuration++;
        }
    }

    @Override
    public void perform(double interpolation) {
        double progress = (currentDuration + interpolation) / duration;
        object.setOpacity(1.0 - progress);
        // the object shrinks from initial `startScale` to
        // `SCALE_COEFFICIENT * startScale`
        object.setScale(startScale
                + progress * (SCALE_COEFFICIENT * startScale - startScale));
        object.setRotationAngle(startAngle + progress * FINAL_ROTATION_ANGLE);
    }

    @Override
    public void perform() {
        perform(0.0);
    }

    @Override
    public boolean finished() {
        return currentDuration >= duration;
    }

    @Override
    public void finish() {
        currentDuration = duration;
        perform();
    }

    @Override
    public int timeLeft() {
        return Math.max(0, duration - currentDuration);
    }
}

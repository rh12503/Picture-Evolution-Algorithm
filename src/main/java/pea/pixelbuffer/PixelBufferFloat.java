package pea.pixelbuffer;

import pea.calculator.FloatPixelComparator;
import pea.calculator.PixelComparator;

public class PixelBufferFloat implements PixelBuffer<PixelBufferFloat> {
    private PixelComparator differenceCalculator = new FloatPixelComparator();
    private float[] pixels;

    public float[] getPixels() {
        return pixels;
    }

    public void setPixels(float[] pixels) {
        this.pixels = pixels;
    }

    @Override
    public double getDifference(PixelBufferFloat other) {
        return differenceCalculator.calculateDifference(this, other);
    }

    @Override
    public double getMaxDifference() {
        return 3;
    }

    @Override
    public int size() {
        return pixels.length;
    }
}

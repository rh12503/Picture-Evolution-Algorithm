package pea.pixelbuffer;

import pea.calculator.LoopPixelComparator;
import pea.calculator.PixelComparator;

public class PixelBufferByte implements PixelBuffer<PixelBufferByte> {

    private PixelComparator differenceCalculator = new LoopPixelComparator();
    private byte[] pixels;

    public byte[] getPixels() {
        return pixels;
    }

    public void setPixels(byte[] pixels) {
        this.pixels = pixels;
    }

    @Override
    public double getDifference(PixelBufferByte other) {
        return differenceCalculator.calculateDifference(this, other);
    }

    @Override
    public double getMaxDifference() {
        return 255 * 255 * 3;
    }

    @Override
    public int size() {
        return pixels.length;
    }
}

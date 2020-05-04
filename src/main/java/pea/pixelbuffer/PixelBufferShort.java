package pea.pixelbuffer;

import pea.calculator.PixelComparator;
import pea.calculator.ShortPixelComparator;

public class PixelBufferShort implements PixelBuffer<PixelBufferShort> {
    public static long MAX_DIFFERENCE = 3L * (long) (Short.MAX_VALUE - Short.MIN_VALUE) * (long) (Short.MAX_VALUE - Short.MIN_VALUE);

    private short[] pixels;
    private PixelComparator pixelComparator = new ShortPixelComparator();

    public short[] getPixels() {
        return pixels;
    }

    public void setPixels(short[] pixels) {
        this.pixels = pixels;
    }

    @Override
    public double getDifference(PixelBufferShort other) {
        return pixelComparator.calculateDifference(this, other);
    }

    @Override
    public double getMaxDifference() {
        return MAX_DIFFERENCE;
    }

    @Override
    public int size() {
        return pixels.length;
    }
}

package pea.calculator;

import pea.pixelbuffer.PixelBufferShort;

public class ShortPixelComparator implements PixelComparator<PixelBufferShort> {

    @Override
    public double calculateDifference(PixelBufferShort a, PixelBufferShort b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Float buffers must be the same length");
        }
        double score = 0;
        short[] h = a.getPixels();
        short[] k = b.getPixels();
        for (int i = 0; i < a.size(); i++) {
            float diff = Math.abs(h[i] - k[i]);
            score += diff * diff;
        }
        return score;

    }
}

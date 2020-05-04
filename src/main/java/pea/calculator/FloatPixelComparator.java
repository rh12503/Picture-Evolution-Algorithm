package pea.calculator;

import pea.pixelbuffer.PixelBufferFloat;

public class FloatPixelComparator implements PixelComparator<PixelBufferFloat> {

    @Override
    public double calculateDifference(PixelBufferFloat a, PixelBufferFloat b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Float buffers must be the same length");
        }
        double score = 0;
        float[] h = a.getPixels();
        float[] k = b.getPixels();
        for (int i = 0; i < a.size(); i++) {
            float diff = Math.abs(h[i] - k[i]);
            score += diff * diff;
        }
        return score;
    }
}

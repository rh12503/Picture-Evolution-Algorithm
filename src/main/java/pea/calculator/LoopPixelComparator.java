package pea.calculator;

import pea.pixelbuffer.PixelBufferByte;

public class LoopPixelComparator implements PixelComparator<PixelBufferByte> {
    @Override
    public double calculateDifference(PixelBufferByte a, PixelBufferByte b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Byte buffers must be the same length");
        }
        long score = 0;
        byte[] h = a.getPixels();
        byte[] k = b.getPixels();
        for (int i = 0; i < a.size(); i++) {
            int diff = Math.abs((h[i] & 0xff) - (k[i] & 0xff));
            score += diff * diff;
        }
        return score;
    }
}

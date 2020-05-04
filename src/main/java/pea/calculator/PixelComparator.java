package pea.calculator;

import pea.pixelbuffer.PixelBuffer;

import java.io.Serializable;

public interface PixelComparator<T extends PixelBuffer<T>> extends Serializable {
     double calculateDifference(T a, T b);
}

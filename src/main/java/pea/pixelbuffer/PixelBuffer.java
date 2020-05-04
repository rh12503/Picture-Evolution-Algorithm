package pea.pixelbuffer;

import java.io.Serializable;

public interface PixelBuffer<T extends PixelBuffer> extends Serializable {
    double getDifference(T other);
    double getMaxDifference();
    int size();
}

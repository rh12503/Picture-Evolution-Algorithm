package pea.ga;

import pea.pixelbuffer.PixelBuffer;

import java.io.Serializable;

public class Image implements Serializable {
    private static final long serialVersionUID = -8036239788761640659L;

    public PixelBuffer pixels;
    public final int width;
    public final int height;

    public Image(int width, int height, PixelBuffer pixels) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }
}
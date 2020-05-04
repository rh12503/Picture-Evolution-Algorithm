package pea.canvas;

import pea.ga.Image;
import pea.pixelbuffer.PixelBuffer;
import pea.util.SimpleColor;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public interface DrawingCanvas {
    void begin();
    void end();
    void background(SimpleColor color);
    void rect(float x, float y, float w, float h);
    void ellipse(float x, float y, float w, float h);
    void circle(float x, float y, float r);
    void polygon(float[] xVertices, float[] yVertices);
    void line(float x1, float y1, float x2, float y2, float strokeWeight);
    void fill(SimpleColor color);
    PixelBuffer getPixels();
    BufferedImage toImage();
    int getWidth();
    int getHeight();
    Image createCompatibleImage(BufferedImage image);
}

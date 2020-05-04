package pea.canvas.g2d;

import pea.canvas.DrawingCanvas;
import pea.ga.Image;
import pea.pixelbuffer.PixelBufferByte;
import pea.util.ImageUtil;
import pea.util.SimpleColor;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Graphics2DCanvas implements DrawingCanvas {

    private final BufferedImage canvas;

    private final int width;
    private final int height;

    private SimpleColor fillColor = new SimpleColor();

    private final Rectangle2D.Float rect = new Rectangle2D.Float();
    private final Ellipse2D.Float ellipse = new Ellipse2D.Float();
    private final Polygon polygon = new Polygon();
    private final Line2D.Float line = new Line2D.Float();

    private Graphics2D g2d = null;
    private PixelBufferByte pixelBuffer = new PixelBufferByte();

    public Graphics2DCanvas(final int width, final int height) {
        this.canvas = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        this.width = width;
        this.height = height;
    }

    @Override
    public void begin() {
        g2d = (Graphics2D) canvas.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    public void end() {
        g2d = null;
    }

    @Override
    public void background(SimpleColor color) {
        fill(color);
        rect(0, 0, width, height);
    }

    @Override
    public void rect(float x, float y, float w, float h) {
        rect.setFrame(x, y, w, h);
        fillShape(rect);
    }

    @Override
    public void ellipse(float x, float y, float rX, float rY) {
        ellipse.setFrame(x - rX, y - rY, rX * 2, rY * 2);
        fillShape(ellipse);
    }

    @Override
    public void circle(float x, float y, float r) {
        float d = r * 2;
        ellipse.setFrame(x - r, y - r, d, d);
        fillShape(ellipse);
    }

    @Override
    public void polygon(float[] xVertices, float[] yVertices) {
        polygon.reset();
        for (int i = 0; i < xVertices.length; i++) {
            polygon.addPoint(Math.round(xVertices[i]), Math.round(yVertices[i]));
        }
        fillShape(polygon);
    }

    @Override
    public void line(float x1, float y1, float x2, float y2, float strokeWeight) {
        line.setLine(x1, y1, x2, y2);
        g2d.setColor(new Color(fillColor.r, fillColor.g, fillColor.b, fillColor.a));
        g2d.setStroke(new BasicStroke(strokeWeight, BasicStroke.CAP_ROUND, 0));
        g2d.draw(line);
    }

    @Override
    public void fill(SimpleColor color) {
        this.fillColor.r = color.r;
        this.fillColor.g = color.g;
        this.fillColor.b = color.b;
        this.fillColor.a = color.a;
    }


    private void fillShape(Shape shape) {
        g2d.setColor(new Color(fillColor.r, fillColor.g, fillColor.b, fillColor.a));
        g2d.fill(shape);
    }

    @Override
    public PixelBufferByte getPixels() {
        byte[] pixels = ((DataBufferByte) canvas.getRaster().getDataBuffer()).getData();

        pixelBuffer.setPixels(pixels);
        return pixelBuffer;
    }

    @Override
    public BufferedImage toImage() {
        return canvas;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Image createCompatibleImage(BufferedImage image) {
        return ImageUtil.createImage(image);
    }
}

package pea.shapes;

import pea.canvas.DrawingCanvas;

public class Polygon extends BaseShape {
    private static final long serialVersionUID = -6924884022742270724L;


    private float[] xVertices;
    private float[] yVertices;

    public Polygon(int vertices) {
        super(vertices * 2);
        xVertices = new float[vertices];
        yVertices = new float[vertices];
    }

    @Override
    public void render(DrawingCanvas dc) {
        super.render(dc);
        for (int i = 0; i < xVertices.length; i++) {
            xVertices[i] = geneAt(i) * dc.getWidth();
            yVertices[i] = geneAt(i + xVertices.length) * dc.getHeight();
        }
        dc.polygon(xVertices, yVertices);
    }

    private float geneAt(int index) {
        return genes[super.getRelativeIndex(index)];
    }
}

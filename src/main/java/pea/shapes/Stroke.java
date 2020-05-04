package pea.shapes;

import pea.canvas.DrawingCanvas;

public class Stroke extends BaseShape {

    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public Stroke() {
        super(5);
    }

    @Override
    public void render(DrawingCanvas dc) {
        super.render(dc);
        x1 = geneAt(0) * dc.getWidth();
        y1 = geneAt(1) * dc.getHeight();
        x2 = geneAt(2) * dc.getWidth();
        y2 = geneAt(3) * dc.getHeight();

        dc.line(x1, y1, x2, y2, geneAt(4) * Math.min(dc.getWidth(), dc.getHeight()));
    }

    private float geneAt(int index) {
        return genes[super.getRelativeIndex(index)];
    }
}

package pea.shapes;

import pea.canvas.DrawingCanvas;

public class Circle extends PositionalShape {
    public Circle() {
        super(1);
    }

    @Override
    public void render(DrawingCanvas dc) {
        super.render(dc);
        float r = geneAt(0) * Math.min(dc.getWidth(), dc.getHeight()) / 2;
        dc.circle(x, y, r);
    }

    private float geneAt(int index) {
        return genes[super.getRelativeIndex(index)];
    }
}


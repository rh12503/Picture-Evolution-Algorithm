package pea.shapes;

import pea.canvas.DrawingCanvas;

public class Ellipse extends PositionalShape {

    public Ellipse() {
        super(2);
    }

    @Override
    public void render(DrawingCanvas dc) {
        super.render(dc);
        dc.ellipse(x, y, geneAt(0) * dc.getWidth() / 2, geneAt(1) * dc.getHeight() / 2);
    }

    private float geneAt(int index) {
        return genes[super.getRelativeIndex(index)];
    }
}

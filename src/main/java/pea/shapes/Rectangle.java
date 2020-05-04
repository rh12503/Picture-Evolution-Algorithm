package pea.shapes;

import pea.canvas.DrawingCanvas;

public class Rectangle extends DimensionalShape {
    public Rectangle() {
        super(0);
    }
    @Override

    public void render(DrawingCanvas dc) {
        super.render(dc);
        dc.rect(x, y, width, height);
    }
}

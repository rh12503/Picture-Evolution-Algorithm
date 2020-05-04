package pea.shapes;

import pea.canvas.DrawingCanvas;

public class Square extends ProportionalShape {
    public Square() {
        super(0);
    }

    @Override
    public void render(DrawingCanvas dc) {
        super.render(dc);
        dc.rect(x, y, diameter, diameter);
    }
}

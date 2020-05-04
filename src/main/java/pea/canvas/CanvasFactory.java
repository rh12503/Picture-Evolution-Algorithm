package pea.canvas;

import java.io.Serializable;

public interface CanvasFactory extends Serializable {
    public DrawingCanvas newCanvas(int width, int height);
}

package pea.ga;

import pea.shapes.Shape;

import java.io.Serializable;

public interface ShapeFactory extends Serializable {
    public Shape newShape();
}

package pea.shapes;

import pea.canvas.DrawingCanvas;

import java.io.Serializable;
import java.util.Random;

public interface Shape extends Comparable<Shape>, Serializable {

    public void set(Shape other);

    public float[] getGenes();

    public void setRandom(Random random);

    public void mutate(final float MUTATION_RATE, final float MUTATION_AMOUNT, Random random);

    public void render(DrawingCanvas dc);
}

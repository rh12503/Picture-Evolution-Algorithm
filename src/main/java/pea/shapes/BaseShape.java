package pea.shapes;

import pea.canvas.DrawingCanvas;
import pea.util.SimpleColor;

import java.util.Random;

public abstract class BaseShape extends DefaultShape {

    public static final int GENE_LENGTH = 5;

    protected SimpleColor color = new SimpleColor();
    protected float z;

    private final int GENE_COUNT;

    public BaseShape(int GENE_LENGTH) {
        super(BaseShape.GENE_LENGTH + GENE_LENGTH);
        GENE_COUNT = BaseShape.GENE_LENGTH + GENE_LENGTH;
    }

    @Override
    public void render(DrawingCanvas dc) {
        //color.set(genes[0],genes[1], genes[2], genes[3]);

        //g2d.setColor(color);
        color.r = geneAt(0);
        color.g = geneAt(1);
        color.b = geneAt(2);
        color.a = geneAt(3);
        dc.fill(color);
    }

    @Override
    public int compareTo(Shape shape) {
        if (!(shape instanceof BaseShape)) {
            return 0;
        }
        BaseShape baseShape = (BaseShape) shape;
        return Float.compare(baseShape.genes[4], genes[4]);
    }

    @Override
    public void setRandom(Random random) {
        super.setRandom(random);
        genes[3] = 0;
    }

    @Override
    public float[] getGenes() {
        return genes;
    }

    private float geneAt(int index) {
        return genes[super.getRelativeIndex(index)];
    }

    @Override
    protected int getRelativeIndex(int index) {
        return super.getRelativeIndex(index)+GENE_LENGTH;
    }

    public float getR() {
        return geneAt(0);
    }

    public float getG() {
        return geneAt(1);
    }

    public float getB() {
        return geneAt(2);
    }

    public float getA() {
        return geneAt(3);
    }
}

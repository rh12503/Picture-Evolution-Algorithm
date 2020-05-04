package pea.shapes;

import pea.canvas.DrawingCanvas;

public abstract class ProportionalShape extends PositionalShape {
    public static final int GENE_LENGTH = 1;

    protected float diameter;

    public ProportionalShape(int GENE_LENGTH) {
        super(ProportionalShape.GENE_LENGTH + GENE_LENGTH);
    }

    @Override
    public void render(DrawingCanvas dc) {
        super.render(dc);
        diameter = geneAt(0) * (Math.min(dc.getWidth(), dc.getHeight()));
        float radius = diameter / 2f;
        x-=radius;
        y-=radius;
    }

    private float geneAt(int index) {
        return genes[super.getRelativeIndex(index)];
    }

    @Override
    protected int getRelativeIndex(int index) {
        return super.getRelativeIndex(index) + GENE_LENGTH;
    }

    public float getDiameter(int width, int height) {
        return geneAt(0) * (Math.min(width, height));
    }
}

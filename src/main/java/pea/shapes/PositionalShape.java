package pea.shapes;

import pea.canvas.DrawingCanvas;

public abstract class PositionalShape extends BaseShape {
    public static final int GENE_LENGTH = 2;

    protected float x;
    protected float y;

    public PositionalShape(int GENE_LENGTH) {
        super(PositionalShape.GENE_LENGTH + GENE_LENGTH);
    }

    @Override
    public void render(DrawingCanvas dc) {
        super.render(dc);
        x = geneAt(0) * dc.getWidth();
        y = geneAt(1) * dc.getHeight();
    }

    private float geneAt(int index) {
        return genes[super.getRelativeIndex(index)];
    }

    @Override
    protected int getRelativeIndex(int index) {
        return super.getRelativeIndex(index)+GENE_LENGTH;
    }

    public float getX(int width) {
        return geneAt(0)*width;
    }

    public float getY(int height) {
        return geneAt(1)*height;
    }
}

package pea.shapes;

import pea.canvas.DrawingCanvas;

public class DimensionalShape extends PositionalShape {
    public static final int GENE_LENGTH = 2;

    protected float width;
    protected float height;

    public DimensionalShape(int GENE_LENGTH) {
        super(GENE_LENGTH + DimensionalShape.GENE_LENGTH);
    }

    @Override
    public void render(DrawingCanvas dc) {
        super.render(dc);
        width = geneAt(0) * dc.getWidth();
        height = geneAt(1) * dc.getHeight();
        x -= width / 2;
        y -= height / 2;
    }

    private float geneAt(int index) {
        return genes[super.getRelativeIndex(index)];
    }

    @Override
    protected int getRelativeIndex(int index) {
        return super.getRelativeIndex(index) + GENE_LENGTH;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}

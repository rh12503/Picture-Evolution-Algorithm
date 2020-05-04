package pea.ga.picture;

import pea.canvas.DrawingCanvas;
import pea.canvas.fastaparapi.FastAparapiCanvas;
import pea.ga.Image;
import pea.ga.ShapeFactory;
import pea.util.SimpleColor;

public class AparapiPicture extends EvolvingPicture {

    private FastAparapiCanvas canvas;
    private final double worstCaseFitness;

    public AparapiPicture(int width, int height, int shapeCount, SimpleColor backgroundColor, ShapeFactory shapeFactory) {
        super(shapeCount, width, height, backgroundColor, shapeFactory);
        canvas = new FastAparapiCanvas(width, height);
        worstCaseFitness = 3d * width * height;
    }

    @Override
    public double calculateFitness(Image target) {
        canvas.setImage(target);
        draw();
        return getPercentageFitness(canvas.getDifference(), worstCaseFitness);
    }

    @Override
    public DrawingCanvas getCanvas() {
        return canvas;
    }

    public static class Builder extends EvolvingPicture.Builder<Builder> {

        public Builder(int width, int height) {
            super(width, height);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public EvolvingPicture build() {
            return new AparapiPicture(width, height, shapeCount, backgroundColor, shapeFactory);
        }
    }
}

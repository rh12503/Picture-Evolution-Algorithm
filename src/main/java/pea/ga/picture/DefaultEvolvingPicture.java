package pea.ga.picture;

import pea.canvas.CanvasFactory;
import pea.canvas.DrawingCanvas;
import pea.canvas.g2d.Graphics2DCanvas;
import pea.ga.Image;
import pea.ga.ShapeFactory;
import pea.util.SimpleColor;

public class DefaultEvolvingPicture extends EvolvingPicture {

    private DrawingCanvas canvas;
    public final double worstCaseFitness;

    public DefaultEvolvingPicture(int width, int height, CanvasFactory canvasFactory, int shapeCount, SimpleColor backgroundColor, ShapeFactory shapeFactory) {
        super(shapeCount, width, height, backgroundColor, shapeFactory);
        canvas = canvasFactory.newCanvas(width, height);
        worstCaseFitness = getCanvas().getPixels().getMaxDifference() * (width * height);
    }

    @Override
    public double calculateFitness(Image target) {
        draw();

        double difference = canvas.getPixels().getDifference(target.pixels);
        return getPercentageFitness(difference, worstCaseFitness);
    }

    @Override
    public DrawingCanvas getCanvas() {
        return canvas;
    }

    public static class Builder extends EvolvingPicture.Builder<Builder> {
        public static CanvasFactory DEFAULT_CANVAS_FACTORY = Graphics2DCanvas::new;

        private CanvasFactory canvasFactory = DEFAULT_CANVAS_FACTORY;

        public Builder(int width, int height) {
            super(width, height);
        }

        public Builder canvasFactory(CanvasFactory canvasFactory) {
            this.canvasFactory = canvasFactory;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public EvolvingPicture build() {
            return new DefaultEvolvingPicture(width, height, canvasFactory, shapeCount, backgroundColor, shapeFactory);
        }
    }
}

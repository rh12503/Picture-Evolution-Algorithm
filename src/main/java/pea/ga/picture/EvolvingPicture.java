package pea.ga.picture;

import pea.canvas.DrawingCanvas;
import pea.canvas.g2d.Graphics2DCanvas;
import pea.ga.Image;
import pea.ga.ShapeFactory;
import pea.shapes.Circle;
import pea.shapes.Shape;
import pea.util.SimpleColor;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public abstract class EvolvingPicture implements Picture {

    public final int shapeCount;
    public final SimpleColor backgroundColor;

    protected final int width;
    protected final int height;

    protected final Shape[] shapes;
    protected final ShapeFactory shapeFactory;

    protected double fitness;

    public EvolvingPicture(int shapeCount, int width, int height, SimpleColor backgroundColor, ShapeFactory shapeFactory) {
        this.shapeFactory = shapeFactory;

        this.shapeCount = shapeCount;
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        shapes = new Shape[shapeCount];
        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = shapeFactory.newShape();
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    protected void sortShapes() {
        Arrays.sort(shapes);
    }


    @Override
    public BufferedImage getImage() {
        Graphics2DCanvas c = new Graphics2DCanvas(getWidth(), getHeight());
        render(c);
        return c.toImage();
    }

    @Override
    public Image createCompatibleImage(BufferedImage image) {
        return getCanvas().createCompatibleImage(image);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof EvolvingPicture)) {
            return false;
        }

        EvolvingPicture picture = (EvolvingPicture) obj;

        if (picture.shapeCount != shapeCount) {
            return false;
        }

        for (int i = 0; i < shapes.length; i++) {
            for (int j = 0; j < shapes[i].getGenes().length; j++) {
                if (shapes[i].getGenes()[j] != picture.shapes[i].getGenes()[j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public void render(DrawingCanvas dc) {
        dc.begin();
        dc.background(backgroundColor);

        for (Shape shape : shapes) {
            shape.render(dc);
        }
        dc.end();
    }

    @Override
    public void mutate(final float mutationRate, final float mutationAmount, Supplier<Random> randomSupplier) {
        for (int i = 0; i < shapeCount; i++) {
            Random random = randomSupplier.get();
            float[] genes =  shapes[i].getGenes();
            for (int j = 0; j < genes.length; j++) {
                if (random.nextDouble() < mutationRate) {
                    double change = random.nextDouble();
                    genes[j] += change * mutationAmount * 2f - mutationAmount;
                    if (genes[j] > 1) {
                        genes[j] = 1;
                    }
                    if (genes[j] < 0) {
                        genes[j] = 0;
                    }
                }
            }
        }
        //System.out.println();
        sortShapes();
    }

    @Override
    public void updateFitness(Image target) {
        fitness = calculateFitness(target);
    }

    @Override
    public void setRandom(Supplier<Random> randomSupplier) {
        for (Shape shape : shapes) {
            shape.setRandom(randomSupplier.get());
        }
    }

    @Override
    public void set(Picture picture) {
        EvolvingPicture other = (EvolvingPicture) picture;
        if (other.shapeCount != shapeCount) {
            throw new UnsupportedOperationException("Pictures must have the same shape count");
        }
        for (int i = 0; i < shapeCount; i++) {
            if (!shapes[i].equals(other.shapes[i])) {
                shapes[i].set(other.shapes[i]);
            }
        }
        fitness = other.getFitness();
    }

    public abstract double calculateFitness(Image target);

    public abstract DrawingCanvas getCanvas();

    public void draw() {
        render(getCanvas());
    }

    public static double getPercentageFitness(double difference, double worstCaseFitness) {
        return 1 - (difference / worstCaseFitness);
    }

    static abstract class Builder<T extends Builder> {
        public final static int DEFAULT_SHAPE_COUNT = 100;
        public final static SimpleColor DEFAULT_BACKGROUND_COLOR = new SimpleColor(1, 1, 1);
        public final static ShapeFactory DEFAULT_SHAPE_FACTORY = Circle::new;

        protected int shapeCount = DEFAULT_SHAPE_COUNT;
        protected final int width;
        protected final int height;
        protected SimpleColor backgroundColor = DEFAULT_BACKGROUND_COLOR;
        protected ShapeFactory shapeFactory = DEFAULT_SHAPE_FACTORY;

        public Builder(final int width, final int height) {
            this.width = width;
            this.height = height;
        }

        public T shapeCount(final int shapeCount) {
            this.shapeCount = shapeCount;
            return self();
        }

        public T backgroundColor(final SimpleColor backgroundColor) {
            this.backgroundColor = backgroundColor;
            return self();
        }

        public T shapeFactory(final ShapeFactory shapeFactory) {
            this.shapeFactory = shapeFactory;
            return self();
        }

        protected abstract T self();

        public abstract EvolvingPicture build();
    }
}

package pea.ga;

import pea.ga.acceptance.AcceptanceStrategy;
import pea.ga.acceptance.HillClimbingStrategy;
import pea.ga.picture.DefaultEvolvingPicture;
import pea.ga.picture.Picture;
import pea.ga.picture.PictureFactory;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class World implements Serializable {
    private static final long serialVersionUID = -7524043572834204083L;

    public final float mutationRate;
    public final float mutationAmount;
    public final Image target;
    private final AcceptanceStrategy acceptanceStrategy;

    private final Picture parent;
    private final transient Picture child;
    private int generation = 0;
    private int improvements = 0;
    private float elapsedTime = 0;

    private Supplier<Random> randomSupplier;

    private final transient List<Observer> observers = new ArrayList<>();

    public World(final AcceptanceStrategy acceptanceStrategy, final float mutationRate, final float mutationAmount, final BufferedImage target, final PictureFactory pictureFactory, Supplier<Random> randomSupplier) {
        this.acceptanceStrategy = acceptanceStrategy;
        this.registerObserver(acceptanceStrategy);
        this.mutationRate = mutationRate;
        this.mutationAmount = mutationAmount;
        this.parent = pictureFactory.newPicture(target.getWidth(), target.getHeight());
        this.target = parent.createCompatibleImage(target);
        this.randomSupplier = randomSupplier;

        child = pictureFactory.newPicture(target.getWidth(), target.getHeight());
    }

    public void setRandom() {
        parent.setRandom(randomSupplier);
        parent.updateFitness(target);
    }

    public void step() {
        long start = System.nanoTime();
        child.set(parent);

        child.mutate(mutationRate, mutationAmount, randomSupplier);

        child.updateFitness(target);
        if (parent.getFitness() < child.getFitness()) {
            improvements++;
            parent.set(child);
        }
        generation++;

        elapsedTime += (System.nanoTime() - start) / 1000000000f;

        notifyObservers();
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public int getGeneration() {
        return generation;
    }

    public int getImprovements() {
        return improvements;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public double getPictureFitness() {
        return parent.getFitness();
    }

    public BufferedImage getImage() {
        return parent.getImage();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof World == false) {
            return false;
        }

        World world = (World) obj;

        return parent.equals(world.parent);
    }

    public String toString() {
        return new StringBuilder().append(generation).append(' ').append(parent.getFitness()).append(' ')
                .append(improvements).append(' ').append(elapsedTime).toString();
    }

    public static class Builder {
        public final static AcceptanceStrategy DEFAULT_ACCEPTANCE_STRATEGY = new HillClimbingStrategy();
        public final static float DEFAULT_MUTATION_RATE = 0.01f;
        public final static float DEFAULT_MUTATION_AMOUNT = 0.2f;
        public final static Supplier<Random> DEFAULT_RANDOM_SUPPLIER = ThreadLocalRandom::current;

        protected AcceptanceStrategy acceptanceStrategy = DEFAULT_ACCEPTANCE_STRATEGY;
        protected float mutationRate = DEFAULT_MUTATION_RATE;
        protected float mutationAmount = DEFAULT_MUTATION_AMOUNT;
        protected final BufferedImage target;
        protected Supplier<Random> randomSupplier = DEFAULT_RANDOM_SUPPLIER; // For debug purposes
        protected PictureFactory pictureFactory = (w, h) -> new DefaultEvolvingPicture.Builder(w, h).build();

        public Builder(final BufferedImage target) {
            this.target = target;
        }

        public Builder acceptanceStrategy(final AcceptanceStrategy acceptanceStrategy) {
            this.acceptanceStrategy = acceptanceStrategy;
            return this;
        }

        public Builder mutationRate(final float mutationRate) {
            this.mutationRate = mutationRate;
            return this;
        }

        public Builder mutationAmount(final float mutationAmount) {
            this.mutationAmount = mutationAmount;
            return this;
        }

        public Builder randomSupplier(final Supplier<Random> randomSupplier) {
            this.randomSupplier = randomSupplier;
            return this;
        }

        public Builder pictureFactory(final PictureFactory pictureFactory) {
            this.pictureFactory = pictureFactory;
            return this;
        }

        public World build() {
            return new World(this.acceptanceStrategy, this.mutationRate, this.mutationAmount, this.target, pictureFactory, randomSupplier);
        }
    }

    public interface Observer {
        void update(World world);
    }

}

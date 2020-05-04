package pea.ga.picture;

import pea.ga.Image;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;
import java.util.function.Supplier;

public interface Picture extends Serializable {
    void setRandom(Supplier<Random> randomSupplier);

    void updateFitness(Image target);

    double getFitness();

    Image createCompatibleImage(BufferedImage image);

    void set(Picture picture);

    void mutate(float mutationRate, float mutationAmount, Supplier<Random> randomSupplier);

    BufferedImage getImage();

    int getWidth();

    int getHeight();
}

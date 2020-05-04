package pea;

import org.junit.jupiter.api.Test;
import pea.canvas.CanvasFactory;
import pea.canvas.aparapi.AparapiCanvas;
import pea.canvas.g2d.Graphics2DCanvas;
import pea.ga.World;
import pea.ga.acceptance.SimulatedAnnealingStrategy;
import pea.ga.picture.DefaultEvolvingPicture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldTest {

    // @Test
    void assertRandomness() throws Exception {
        BufferedImage image = ImageIO.read(new File("monalisa.png"));
        assertEquals(runWorldAndGetFitness(image, 200, AparapiCanvas::new, 777), runWorldAndGetFitness(image, 200, AparapiCanvas::new, 777));
    }

    @Test
    void geneticsTest() throws Exception {
        BufferedImage image = ImageIO.read(new File("monalisa.png"));
        World w1;
        World w2;

        System.out.println("World 1");
        w1 = runWorldAndGetWorld(image, 14, Graphics2DCanvas::new, 0);
        System.out.println("World 2");
        w2 = runWorldAndGetWorld(image, 14, AparapiCanvas::new, 0);
        System.out.println("END");

        saveImage(w1.getImage(), "g2d");
        saveImage(w2.getImage(), "CL");
        assertEquals(w1.getPictureFitness(), w2.getPictureFitness(), 0.01);
    }

    @Test
    void renderingTest() throws Exception {
        BufferedImage image = ImageIO.read(new File("monalisa.png"));
        double real = runWorldAndGetFitness(image, 100, Graphics2DCanvas::new, 787);
        assertEquals(0.7190542268672175, real, 0.01);
    }

    private static double runWorldAndGetFitness(BufferedImage image, int generations, CanvasFactory factory, int seed) {
        return runWorldAndGetWorld(image, generations, factory, seed).getPictureFitness();
    }

    private static World runWorldAndGetWorld(BufferedImage image, int generations, CanvasFactory factory, int seed) {
        World world = new World.Builder(image).pictureFactory((w, h) -> new DefaultEvolvingPicture.Builder(w, h).canvasFactory(factory).build()).acceptanceStrategy(new SimulatedAnnealingStrategy()).mutationAmount(0.2f).randomSupplier(new RandomSupplier(seed)).build();
        world.setRandom();

        while (world.getGeneration() < generations) {
            System.out.println(world.getGeneration() + "\t" + world.getPictureFitness());
            world.step();
        }
        return world;
    }

    private static boolean saveImage(BufferedImage canvas, String name) {
        try {
            ImageIO.write(canvas, "png", new File(name + ".png"));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static class RandomSupplier implements Supplier<Random> {
        private Random random;

        public RandomSupplier(int seed) {
            random = new Random(seed);
        }

        @Override
        public Random get() {
            return random;
        }
    }
}